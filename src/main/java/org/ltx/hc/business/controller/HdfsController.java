package org.ltx.hc.business.controller;

import org.ltx.hc.business.util.HdfsUtil;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltxlouis
 * @since 4/3/2018
 */
@RestController
@RequestMapping("/hc")
public class HdfsController {

    private final HdfsUtil hdfsUtil;

    @Autowired
    public HdfsController(HdfsUtil hdfsUtil) {
        this.hdfsUtil = hdfsUtil;
    }

    @PostMapping("/uploadToHdfs")
    public ApiResult uploadToHdfs(HttpServletRequest request, @RequestParam("file") MultipartFile file, String destPath)
            throws IllegalStateException, IOException {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();

                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(originalFilename)));

                out.write(file.getBytes());

                out.flush();
                out.close();

                String destFileName = destPath + originalFilename;

                hdfsUtil.uploadFile(originalFilename, destFileName);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ApiResult.error("0", "FileNotFound F, " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return ApiResult.error("0", "IOExp F, " + e.getMessage());
            }
            return ApiResult.ok("upload success!");
        }
        return ApiResult.error("0", "empty file");
    }

    @GetMapping("/downloadFromHdfs")
    public ApiResult downloadFromHdfs(HttpServletResponse res, String sourcePath) {
        try {
            hdfsUtil.downloadFile(res, sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("0", "IOExp F " + e.getMessage());
        }
        return ApiResult.ok("success!");
    }

    @PostMapping("/deleteFromHdfs")
    public ApiResult deleteFromHdfs(@RequestBody Map<String, String> map) {
        try {
            hdfsUtil.deleteFile(map.get("path"));
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("0", "deleteFromHdfs IOExp failed");
        }
        return ApiResult.ok("delete success!");
    }

    @PostMapping("/deleteFilesFromHdfs")
    public ApiResult deleteFilesFromHdfs(@RequestBody Map<String, List<String>> map) {
        List<String> list = map.get("list");
        try {
            for (String s : list) {
                hdfsUtil.deleteFile(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("0", "deleteFromHdfs IOExp failed");
        }
        return ApiResult.ok("delete success");
    }

    @GetMapping("/getFileList")
    public ApiResult getFileList(@RequestParam String sourcePath) {
        try {
            ArrayList<String> fileList = hdfsUtil.getFileList(sourcePath);
            HashMap<Object, Object> hashMap = new HashMap<>(1);
            ArrayList<Object> list = new ArrayList<>();
            for (String filename : fileList) {
                HashMap<Object, Object> map = new HashMap<>(1);
                map.put("file", filename);
                list.add(map);
            }
            hashMap.put("fileList", list);
            return ApiResult.ok(hashMap);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("0", "getFileList failed!");
        }
    }

}
