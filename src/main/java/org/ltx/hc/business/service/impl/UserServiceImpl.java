package org.ltx.hc.business.service.impl;

import org.ltx.hc.business.dao.UserDao;
import org.ltx.hc.business.entity.UserEntity;
import org.ltx.hc.business.oauth.util.BcrytUtil;
import org.ltx.hc.business.repository.UserRepository;
import org.ltx.hc.business.service.FileService;
import org.ltx.hc.business.service.UserService;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    private final FileService fileService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDao userDao, FileService fileService) {
        this.userRepository = userRepository;
        this.userDao = userDao;
        this.fileService = fileService;
    }

    @Override
    public ApiResult addUser(UserEntity userEntity) throws Exception {
        if (userDao.findFirstById(userEntity.getId()) != null) {
            return ApiResult.error("0", "user exists!");
        }
        userEntity.setIsValid(1);
        String password = userEntity.getPassword();
        userEntity.setPassword(BcrytUtil.bEncrypt(password));
        String timeString = new Timestamp(System.currentTimeMillis()).toString();
        userEntity.setTimeCreated(timeString);
        userEntity.setTimeModified(timeString);
        UserEntity save = userRepository.save(userEntity);
        if (save == null) {
            return ApiResult.error("0", "create user failed");
        }
        return fileService.initUserFileTree(userEntity.getId());
//        UserVo vo = UserVo.builder().id(save.getId())
//                .username(nullToBlank(save.getUsername()))
//                .gender(nullToBlank(save.getGender()))
//                .age(nullToBlankInt(save.getAge()))
//                .email(nullToBlank(save.getEmail()))
//                .isValid(save.getIsValid())
//                .timeCreated(save.getTimeCreated())
//                .timeModified(save.getTimeModified())
//                .build();
//        return ApiResult.ok(vo);
    }

    @Override
    public ApiResult chgPwd(String id, String plainPassword, String newPassword) throws Exception {
        UserEntity firstById = userDao.findFirstById(id);
        if (BcrytUtil.bCheckPassword(plainPassword, firstById.getPassword())) {
            firstById.setPassword(BcrytUtil.bEncrypt(newPassword));
            String timeString = new Timestamp(System.currentTimeMillis()).toString();
            firstById.setTimeModified(timeString);
            UserEntity save = userRepository.save(firstById);
            return ApiResult.ok(save);
        }
        return ApiResult.error("0", "update failed!");
    }

    @Override
    public ApiResult chkId(String id) throws Exception {
        if (userDao.findFirstById(id) == null) {
            return ApiResult.ok("id available");
        } else {
            return ApiResult.error("0", "id already exists");
        }
    }

    private String nullToBlank(String s) {
        return s == null ? "" : s;
    }

    private Integer nullToBlankInt(Integer integer) {
        return integer == null ? Integer.valueOf(0) : integer;
    }
}
