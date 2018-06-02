package org.ltx.hc.business.controller;

import org.ltx.hc.business.entity.UserEntity;
import org.ltx.hc.business.service.UserService;
import org.ltx.hc.sys.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@RestController
@RequestMapping("/hc")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public ApiResult addUser(@RequestBody @Validated UserEntity userEntity) throws Exception {
        return userService.addUser(userEntity);
    }

    @PostMapping("/user/chgPwd")
    public ApiResult chgPwd(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("id");
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        return userService.chgPwd(id, oldPassword, newPassword);
    }

    @GetMapping("/user/chkId")
    public ApiResult chkId(@RequestParam String id) throws Exception {
        return userService.chkId(id);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
