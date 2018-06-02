package org.ltx.hc.business.service.impl;

import org.ltx.hc.business.dao.FileDao;
import org.ltx.hc.business.entity.FileEntity;
import org.ltx.hc.business.service.FileService;
import org.ltx.hc.business.vo.FileTreeVo;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author ltxlouis
 * @since 4/13/2018
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;

    @Autowired
    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public ApiResult initUserFileTree(String userId) throws Exception {
        String rootNodeParentId = "-1";
        if (!fileDao.findAllByUserIdAndParentId(userId, rootNodeParentId).isEmpty()) {
            return ApiResult.error("0", "tree already initialized!");
        }
        String currentTime = new Timestamp(System.currentTimeMillis()).toString();
        FileEntity entity = FileEntity.builder()
                .userId(userId)
                .parentId(rootNodeParentId)
                .title(userId + "_root")
                .origTitle(userId + "_root")
                .lft(1)
                .rgt(2)
                .nodeLevel(0)
                .isFile(0)
                .timeCreated(currentTime)
                .timeModified(currentTime).build();
        FileEntity save = fileDao.save(entity);
        if (save != null) {
            return ApiResult.ok(save);
        } else {
            return ApiResult.error("0", "initialize user tree failed!");
        }
    }

    @Override
    public ApiResult addFileNode(String parentId,
                                 String title,
                                 int isFile) throws Exception {
        FileEntity parentEntity = fileDao.findFirstById(parentId);
        if (parentEntity == null) {
            return ApiResult.error("0", "file structure with this id doesn't exit!");
        }
        if (parentEntity.getIsFile() == 1) {
            return ApiResult.error("0", "cannot insert under file type!");
        }
        Integer rgt = parentEntity.getRgt();
        String userId = parentEntity.getUserId();
        String timeString = new Timestamp(System.currentTimeMillis()).toString();
        FileEntity build = FileEntity.builder()
                .userId(parentEntity.getUserId())
                .parentId(parentId)
                .title(title)
                .origTitle(title)
                .lft(rgt)
                .rgt(rgt + 1)
                .nodeLevel(parentEntity.getNodeLevel() + 1)
                .isFile(isFile)
                .timeCreated(timeString)
                .timeModified(timeString).build();
        List<FileEntity> lftGreaterThan =
                fileDao.findAllByUserIdAndLftGreaterThan(userId, rgt - 1);
        List<FileEntity> rgtGreaterThan =
                fileDao.findAllByUserIdAndRgtGreaterThan(userId, rgt - 1);
        FileEntity save = fileDao.save(build);
        if (save == null) {
            return ApiResult.error("0", "create file structure failed!");
        }
        for (FileEntity fse : lftGreaterThan) {
            Integer olft = fse.getLft();
            fse.setLft(olft + 2);
        }
        for (FileEntity fse : rgtGreaterThan) {
            Integer orgt = fse.getRgt();
            fse.setRgt(orgt + 2);
        }
        return ApiResult.ok(save);
    }

    @Override
    public ApiResult addMultipleFileNodes(String parentId, int isFile, ArrayList<String> titleList) throws Exception {
        FileEntity parentFolder = fileDao.findFirstById(parentId);
        if (parentFolder == null || parentFolder.getIsFile() == 1) {
            return ApiResult.error("0", "parent folder error!");
        }
        Integer rgt = parentFolder.getRgt();
        Integer leftTemp = rgt;
        int size = titleList.size();
        String userId = parentFolder.getUserId();
        List<FileEntity> lftGreater =
                fileDao.findAllByUserIdAndLftGreaterThan(userId, rgt - 1);
        List<FileEntity> rgtGreater =
                fileDao.findAllByUserIdAndRgtGreaterThan(userId, rgt - 1);
        FileEntity build;
        FileEntity save;
        ArrayList<FileEntity> arrayList = new ArrayList<>();
        String timeString = new Timestamp(System.currentTimeMillis()).toString();
        for (String titleString : titleList) {
            build = FileEntity.builder()
                    .userId(userId)
                    .parentId(parentId)
                    .title(titleString)
                    .origTitle(titleString)
                    .lft(leftTemp++)
                    .rgt(leftTemp++)
                    .nodeLevel(parentFolder.getNodeLevel() + 1)
                    .isFile(isFile)
                    .timeCreated(timeString)
                    .timeModified(timeString).build();
            save = fileDao.save(build);
            if (save == null) {
                return ApiResult.error("0", "insert failed!");
            }
            arrayList.add(save);
        }
        Integer tempInt;
        for (FileEntity fs : lftGreater) {
            tempInt = fs.getLft();
            fs.setLft(tempInt + 2 * size);
        }
        for (FileEntity fs : rgtGreater) {
            tempInt = fs.getRgt();
            fs.setRgt(tempInt + 2 * size);
        }
        return ApiResult.ok(arrayList);
    }

    @Override
    public ApiResult moveFileNode(String baseId, String destId) throws Exception {
        //todo
        return null;
    }

    @Override
    public ApiResult delFileNode(String nodeId) throws Exception {
        FileEntity firstById = fileDao.findFirstById(nodeId);
        if (firstById == null) {
            return ApiResult.error("0", "node with this id doesn't exit!");
        }
        Integer lft = firstById.getLft();
        Integer rgt = firstById.getRgt();
        String userId = firstById.getUserId();
        int width = rgt - lft + 1;
        List<FileEntity> rgtGreaterThan =
                fileDao.findAllByUserIdAndRgtGreaterThan(userId, rgt);
        List<FileEntity> lftGreaterThan =
                fileDao.findAllByUserIdAndLftGreaterThan(userId, rgt);
        List<FileEntity> allByLftIsBetween =
                fileDao.findAllByUserIdAndLftIsBetween(userId, lft, rgt);
        fileDao.delete(allByLftIsBetween);
        Integer tempInt;
        for (FileEntity fse : rgtGreaterThan) {
            tempInt = fse.getRgt();
            fse.setRgt(tempInt - width);
        }
        for (FileEntity fse : lftGreaterThan) {
            tempInt = fse.getLft();
            fse.setLft(tempInt - width);
        }
        return ApiResult.ok("del success");
    }

    @Override
    public ApiResult delFileNodes(List<String> nodeIdList) throws Exception {
        for (String string : nodeIdList) {
//            ApiResult apiResult = delFileNode(string);
            delFileNode(string);
//            if ("F".equals(apiResult.getFlag())) {
//                return ApiResult.error("0", "del nodes failed!");
//            }
        }
        return ApiResult.ok("del nodes success!");
    }

    @Override
    public ApiResult chgFileNodeName(String id, String newName) throws Exception {
        FileEntity firstById = fileDao.findFirstById(id);
        if (firstById == null) {
            return ApiResult.error("0", "node with this id doesn't exit!");
        }
        firstById.setTitle(newName);
        firstById.setTimeModified(new Timestamp(System.currentTimeMillis()).toString());
        return ApiResult.ok(firstById);
    }

    @Override
    public ApiResult getUserFileTree(String userId) throws Exception {
        FileEntity byUidPid = fileDao.findFirstByUserIdAndParentId(userId, "-1");
        if (byUidPid == null) {
            return ApiResult.error("0", "file structure with this id is not initialized!");
        }
        FileTreeVo fileTreeVo = generateTree(fileEntityToVo(byUidPid));
        return ApiResult.ok(fileTreeVo);
    }

    private FileTreeVo generateTree(FileTreeVo fileTreeVo) {
        if (fileTreeVo.getRgt() - fileTreeVo.getLft() == 1) {
            return fileTreeVo;
        } else {
            List<FileEntity> allByUserIdAndNodeLevel =
                    fileDao.findAllByUserIdAndParentId(fileTreeVo.getUserId(), fileTreeVo.getId());
            if (!allByUserIdAndNodeLevel.isEmpty()) {
                for (FileEntity fe : allByUserIdAndNodeLevel) {
                    fileTreeVo.addChild(generateTree(fileEntityToVo(fe)));
                }
            }
            return fileTreeVo;
        }
    }

    private FileTreeVo fileEntityToVo(FileEntity fileEntity) {
        return FileTreeVo.builder()
                .id(fileEntity.getId())
                .userId(fileEntity.getUserId())
                .parentId(fileEntity.getParentId())
                .title(fileEntity.getTitle())
                .origTitle(fileEntity.getOrigTitle())
                .lft(fileEntity.getLft())
                .rgt(fileEntity.getRgt())
                .nodeLevel(fileEntity.getNodeLevel())
                .isFile(fileEntity.getIsFile())
                .timeCreated(fileEntity.getTimeCreated())
                .timeModified(fileEntity.getTimeModified())
                .children(new ArrayList<>()).build();
    }

    @Override
    public ResponseEntity delFileNodesTest(List<String> nodeIdList) {
        return null;
    }

    @Override
    public ApiResult getUserFileListByDirectory(String nodeId) throws Exception {
        FileEntity firstById = fileDao.findFirstById(nodeId);
        if (firstById == null || firstById.getIsFile() == 1) {
            return ApiResult.error("0", "id does not exist");
        }
        return ApiResult.ok(fileDao.findAllByParentId(nodeId));
    }

    @Override
    public ApiResult getUserRootFileListByUserId(String userId) throws Exception {
        FileEntity userRoot = fileDao.findFirstByUserIdAndParentId(userId, "-1");
        if (userRoot == null || userRoot.getIsFile() == 1) {
            return ApiResult.error("0", "root id is wrong");
        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("id", userRoot.getId());
        map.put("list", fileDao.findAllByParentId(userRoot.getId()));
        return ApiResult.ok(map);
    }

    @Override
    public ApiResult getFileFullPath(String nodeId) throws Exception {
        FileEntity firstById = fileDao.findFirstById(nodeId);
        if (firstById == null) {
            return ApiResult.error("0", "id error");
        }
        Integer lft = firstById.getLft();
        Integer rgt = firstById.getRgt();
        String userId = firstById.getUserId();
        List<FileEntity> list =
                fileDao.findAllByUserIdAndLftLessThanEqualAndRgtGreaterThanEqualOrderByLftAsc(userId, lft, rgt);
        return ApiResult.ok(list);
    }

    @Override
    public ApiResult getUserFileListGoBack(String nodeId) throws Exception {
        FileEntity firstById = fileDao.findFirstById(nodeId);
        if (firstById == null) {
            return ApiResult.error("0", "id error");
        }

        String parentId = firstById.getParentId();
        FileEntity pId = fileDao.findFirstById(parentId);
        if (pId == null) {
            return ApiResult.error("0", "id error");
        }
        String parentId1 = pId.getParentId();
        String rootParentId = "-1";
        HashMap<String, Object> map;
        // root node
        if (Objects.equals(parentId1, rootParentId)) {
            map = new HashMap<>(2);
            map.put("id", pId.getId());
            map.put("list", fileDao.findAllByParentId(pId.getId()));
            return ApiResult.ok(map);
        }

        map = new HashMap<>(2);
        map.put("id", parentId1);
        map.put("list", fileDao.findAllByParentId(parentId1));
        return ApiResult.ok(map);
    }

    @Override
    public ApiResult updateFileTitle(String nodeId, String title) throws Exception {
        FileEntity firstById = fileDao.findFirstById(nodeId);
        if (firstById == null) {
            return ApiResult.error("0", "id error");
        }
        firstById.setTitle(title);
        firstById.setTimeModified(new Timestamp(System.currentTimeMillis()).toString());
        return ApiResult.ok(firstById);
    }

    @Override
    public ApiResult searchFiles(String userId, String title) throws Exception {
        return ApiResult.ok(fileDao.findByUserIdAndTitleLike(userId, "%" + title + "%"));
    }
}
