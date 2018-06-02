package org.ltx.hc.business.oauth.service;

import org.ltx.hc.business.dao.UserDao;
import org.ltx.hc.business.entity.UserEntity;
import org.ltx.hc.business.oauth.dto.OauthReturnInfo;
import org.ltx.hc.business.oauth.util.BcrytUtil;
import org.ltx.hc.business.oauth.util.Md5Util;
import org.ltx.hc.sys.service.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Service
public class OauthService {

    private final CacheUtil cacheUtil;

    private final UserDao userDao;

    @Autowired
    public OauthService(CacheUtil cacheUtil, UserDao userDao) {
        this.cacheUtil = cacheUtil;
        this.userDao = userDao;
    }

    public OauthReturnInfo getRefreshToken(String id, String plainPassword) {
        UserEntity firstById = userDao.findFirstById(id);
        OauthReturnInfo ori = null;
        if (firstById != null && firstById.getIsValid() == 1) {
            if (BcrytUtil.bCheckPassword(plainPassword, firstById.getPassword())) {
                ori = new OauthReturnInfo();
                ori.setFlag("T");
                String token = Md5Util.encode(id, firstById.getPassword(), new Date().toString());
                ori.setRefreshToken(token);
                cacheUtil.set(token, firstById);
            }
        }
        return ori;
    }

    public UserEntity getUserInfo(String refreshToken) {
        return cacheUtil.get(refreshToken, UserEntity.class);
    }

    public UserEntity getUserInfo() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String hcToken = request.getHeader("hcToken");
        return cacheUtil.get(hcToken, UserEntity.class);
    }

    public void kickout(String refreshToken) {
        cacheUtil.remove(refreshToken);
    }

}
