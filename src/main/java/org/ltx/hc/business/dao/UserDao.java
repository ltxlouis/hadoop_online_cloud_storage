package org.ltx.hc.business.dao;

import org.ltx.hc.business.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {

    /**
     * find User by id
     *
     * @param id id
     * @return userEntity
     */
    UserEntity findFirstById(String id);

    /**
     * find by id and password
     *
     * @param id       id
     * @param password password
     * @return userEntity
     */
    UserEntity findFirstByIdAndPassword(String id, String password);

}
