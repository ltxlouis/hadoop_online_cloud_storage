package org.ltx.hc.business.service;

import org.ltx.hc.sys.util.ApiResult;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltxlouis
 * @since 4/13/2018
 */
public interface FileService {
    /**
     * initialize root node with user id
     *
     * @param userId user_id
     * @return ApiResult result
     * @throws Exception e
     */
    ApiResult initUserFileTree(String userId) throws Exception;

    /**
     * add file structure
     *
     * @param parentId parent_id under which you want to insert
     * @param title    title
     * @param isFile   is_file
     * @return apiResult result
     * @throws Exception e
     */
    ApiResult addFileNode(String parentId,
                          String title,
                          int isFile) throws Exception;

    /**
     * add multiple files
     *
     * @param parentId  parent_id
     * @param isFile    is file or not
     * @param titleList title list
     * @return apiResult
     * @throws Exception e
     */
    ApiResult addMultipleFileNodes(String parentId,
                                   int isFile,
                                   ArrayList<String> titleList) throws Exception;

    /**
     * move baseId node under destId
     *
     * @param baseId base node id
     * @param destId destination node id
     * @return apiResult
     * @throws Exception e
     */
    ApiResult moveFileNode(String baseId, String destId) throws Exception;

    /**
     * delete node and all its sub-nodes
     *
     * @param nodeId node_id
     * @return apiResult
     * @throws Exception e
     */
    ApiResult delFileNode(String nodeId) throws Exception;

    /**
     * delete multiple nodes
     *
     * @param nodeIdList node list
     * @return api result
     * @throws Exception e
     */
    ApiResult delFileNodes(List<String> nodeIdList) throws Exception;

    /**
     * response entity test
     *
     * @param nodeIdList node id list
     * @return re
     */
    ResponseEntity delFileNodesTest(List<String> nodeIdList);

    /**
     * update node's title name
     *
     * @param id      node's id
     * @param newName new title name
     * @return api result
     * @throws Exception e
     */
    ApiResult chgFileNodeName(String id, String newName) throws Exception;

    /**
     * get file tree by user_id
     *
     * @param userId user_id
     * @return api result FileTreeVo
     * @throws Exception e
     */
    ApiResult getUserFileTree(String userId) throws Exception;

    /**
     * get file list under the given directory
     *
     * @param nodeId node directory id
     * @return file list
     * @throws Exception e
     */
    ApiResult getUserFileListByDirectory(String nodeId) throws Exception;

    /**
     * get root file list by user id
     *
     * @param userId user_id
     * @return list
     * @throws Exception e
     */
    ApiResult getUserRootFileListByUserId(String userId) throws Exception;

    /**
     * get file/directory full path starting from root
     *
     * @param nodeId node id
     * @return list
     * @throws Exception e
     */
    ApiResult getFileFullPath(String nodeId) throws Exception;

    /**
     * go back to last directory
     *
     * @param nodeId node_id
     * @return api result
     * @throws Exception e
     */
    ApiResult getUserFileListGoBack(String nodeId) throws Exception;

    /**
     * change title
     *
     * @param nodeId node id
     * @param title  title
     * @return result
     * @throws Exception e
     */
    ApiResult updateFileTitle(String nodeId, String title) throws Exception;

    /**
     * fuzzy search
     *
     * @param userId   user id
     * @param fileName file name
     * @return result
     * @throws Exception e
     */
    ApiResult searchFiles(String userId, String fileName) throws Exception;
}
