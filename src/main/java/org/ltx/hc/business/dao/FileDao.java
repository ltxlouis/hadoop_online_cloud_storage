package org.ltx.hc.business.dao;

import org.ltx.hc.business.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ltxlouis
 * @since 4/13/2018
 */
@Repository
public interface FileDao extends JpaRepository<FileEntity, String> {

    /**
     * find by id
     *
     * @param id id
     * @return entity
     */
    FileEntity findFirstById(String id);

    /**
     * find files by parent id
     *
     * @param parentId parent_id
     * @return entity
     */
    List<FileEntity> findAllByParentId(String parentId);

    /**
     * find by user_id and parent_id
     *
     * @param userId   user_id
     * @param parentId parent_id
     * @return list
     */
    List<FileEntity> findAllByUserIdAndParentId(String userId, String parentId);

    /**
     * find by user_id and left greater than
     *
     * @param userId     user_id
     * @param lftGreater left greater than
     * @return list
     */
    List<FileEntity> findAllByUserIdAndLftGreaterThan(String userId, Integer lftGreater);

    /**
     * find by user_id and right greater than
     *
     * @param userId     user_id
     * @param rgtGreater right greater than
     * @return list
     */
    List<FileEntity> findAllByUserIdAndRgtGreaterThan(String userId, Integer rgtGreater);

    /**
     * find nodes that lft between intGreater and intLessThan
     *
     * @param intGreater  greater
     * @param intLessThan less
     * @return list
     */
    List<FileEntity> findAllByLftIsBetween(Integer intGreater, Integer intLessThan);

    /**
     * find by left between
     *
     * @param userId      user id
     * @param intGreater  greater
     * @param intLessThan less
     * @return list
     */
    List<FileEntity> findAllByUserIdAndLftIsBetween(String userId, Integer intGreater, Integer intLessThan);

    /**
     * find by user_id and parent_id
     *
     * @param userId   user_id
     * @param parentId parent_id
     * @return entity
     */
    FileEntity findFirstByUserIdAndParentId(String userId, String parentId);

    /**
     * find by user_id and node_level
     *
     * @param userId    user_id
     * @param nodeLevel node_level
     * @return list
     */
    List<FileEntity> findAllByUserIdAndNodeLevel(String userId, Integer nodeLevel);

    /**
     * get full path of the given node
     *
     * @param userId user_id
     * @param lft    lft
     * @param rgt    rgt
     * @return list order by lft
     */
    List<FileEntity> findAllByUserIdAndLftLessThanEqualAndRgtGreaterThanEqualOrderByLftAsc(String userId,
                                                                                           Integer lft,
                                                                                           Integer rgt);

    /**
     * fuzzy search
     *
     * @param userId
     * @param title
     * @return
     */
    List<FileEntity> findByUserIdAndTitleLike(String userId, String title);

    @Query(nativeQuery = true, value = "select a.* from hc_file a left join hc_user b on a.id = b.id")
    void test();

}
