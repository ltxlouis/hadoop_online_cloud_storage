package org.ltx.hc.business.service;

import org.ltx.hc.business.entity.UserEntity;
import org.ltx.hc.sys.util.ApiResult;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
public interface UserService {
    /**
     * add user
     *
     * @param userEntity user
     * @return user entity
     * @throws Exception e
     */
    ApiResult addUser(UserEntity userEntity) throws Exception;

    /**
     * change password
     *
     * @param id            id
     * @param plainPassword fp
     * @param newPassword   np
     * @return apiResult
     * @throws Exception e
     */
    ApiResult chgPwd(String id, String plainPassword, String newPassword) throws Exception;

    /**
     * check if the id already exists
     *
     * @param id id key
     * @return apiResult
     * @throws Exception e
     */
    ApiResult chkId(String id) throws Exception;
}
