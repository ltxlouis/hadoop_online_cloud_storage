package org.ltx.hc.business.controller;

import org.ltx.hc.business.service.FileService;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ltxlouis
 * @since 4/13/2018
 */
@RestController
@RequestMapping("/hc")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/initUserTree")
    public ApiResult initUserTree(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("id");
        return fileService.initUserFileTree(id);
    }

    @PostMapping("/addNode")
    public ApiResult addNode(@RequestBody Map<String, String> map) throws Exception {
        String parentId = map.get("parentId");
        String title = map.get("title");
        int isFile = Integer.parseInt(map.get("isFile"));
        return fileService.addFileNode(parentId, title, isFile);
    }

    @PostMapping("/addMultiple")
    public ApiResult addMultiple(@RequestBody Map<String, Object> map) throws Exception {
        String parentId = map.get("parentId").toString();
        int isFile = Integer.parseInt(String.valueOf(map.get("isFile")));
        ArrayList titleList = (ArrayList) map.get("titleList");
        return fileService.addMultipleFileNodes(parentId, isFile, titleList);
    }

    @PostMapping("/delNode")
    public ApiResult delNode(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("id");
        return fileService.delFileNode(id);
    }

    @PostMapping("/delNodes")
    public ApiResult delNodeList(@RequestBody Map<String, List<String>> map) throws Exception {
        List<String> list = map.get("idList");
        return fileService.delFileNodes(list);
    }

    @GetMapping("/getUserFileTree")
    public ApiResult getUserFileTree(@RequestParam String userId) throws Exception {
        return fileService.getUserFileTree(userId);
    }

    @GetMapping("/getFileListByNodeId")
    public ApiResult getUserFileListByDirectory(@RequestParam String id) throws Exception {
        return fileService.getUserFileListByDirectory(id);
    }

    @GetMapping("/getUserRootList")
    public ApiResult getUserRootFileListByUserId(@RequestParam String userId) throws Exception {
        return fileService.getUserRootFileListByUserId(userId);
    }

    @GetMapping("/getFileFullPath")
    public ApiResult getFileFullPath(@RequestParam String id) throws Exception {
        return fileService.getFileFullPath(id);
    }

    @GetMapping("/getUserFileListGoBack")
    public ApiResult getUserFileListGoBack(@RequestParam String id) throws Exception {
        return fileService.getUserFileListGoBack(id);
    }

    @PostMapping("/updateFileTitle")
    public ApiResult updateFileTitle(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("id");
        String title = map.get("title");
        return fileService.updateFileTitle(id, title);
    }

    @PostMapping("/searchFiles")
    public ApiResult searchFiles(@RequestBody Map<String, String> map) throws Exception {
        String userId = map.get("userId");
        String title = map.get("title");
        return fileService.searchFiles(userId, title);
    }

}
