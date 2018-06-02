package org.ltx.hc.sys.service;

import org.ltx.hc.business.entity.UserEntity;
import org.ltx.hc.business.oauth.dto.OauthReturnInfo;
import org.ltx.hc.business.oauth.service.OauthService;
import org.ltx.hc.sys.exception.UserOauthException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Service
public class ApiService {
    @Resource
    private OauthService oauthService;

    public Map<String, String> handleLogin(Map<String, String> requestMap) {
        Map<String, String> res = new HashMap<>(2);
        String trueFlag = "T";
        String loginId = requestMap.get("id");
        String password = requestMap.get("password");
        OauthReturnInfo ri = oauthService.getRefreshToken(loginId, password);
        if (null == ri) {
            throw new UserOauthException(loginId);
        }
        if (trueFlag.equals(ri.getFlag())) {
            res.put("rt", ri.getRefreshToken());
            res.put("id", loginId);
            return res;
        } else {
            throw new UserOauthException(loginId);
        }
    }

    public void handleLogout(String token) {
        oauthService.kickout(token);
    }

    public UserEntity getUserInfo(String token) {
        return oauthService.getUserInfo(token);
    }

}
