package org.ltx.hc.business.repository;

import org.ltx.hc.business.entity.UserEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@Repository
public class UserRepository extends SimpleJpaRepository<UserEntity, String> {

    public UserRepository(EntityManager em) {
        super(UserEntity.class, em);
    }
}
