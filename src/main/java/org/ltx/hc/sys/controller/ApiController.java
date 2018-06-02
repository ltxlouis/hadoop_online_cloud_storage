package org.ltx.hc.sys.controller;

import org.ltx.hc.sys.service.ApiService;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ltxlouis
 */
@CrossOrigin
@RestController
@RequestMapping("/hc")
public class ApiController {

    @Resource
    private ApiService apiService;

    @PostMapping("/signIn")
    public ApiResult signIn(@RequestBody Map<String, String> map) {
        //        try {
        //            Map<String, String> dataMap = apiService.handleLogin(map);
        //            result = ApiResult.ok(dataMap);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            result = ApiResult.error("0", "login failed!");
        //        }
        //        return result;
        Map<String, String> dataMap = apiService.handleLogin(map);
        return ApiResult.ok(dataMap);
    }

    @PostMapping("/signOut")
    public ApiResult signOut(@RequestBody Map<String, String> map) {
        ApiResult result;
        try {
            apiService.handleLogout(map.get("hcToken"));
            result = ApiResult.ok("logout success!");
        } catch (Exception e) {
            e.printStackTrace();
            result = ApiResult.error("0", "logout failed!");
        }
        return result;
    }
}
