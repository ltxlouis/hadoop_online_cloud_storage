package org.ltx.hc.business.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ltx.hc.business.oauth.service.OauthService;
import org.ltx.hc.sys.exception.UserOauthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Component
@Aspect
@Slf4j
public class SignAspect {

    private final OauthService oauthService;
    private static final Boolean DEBUG = true;

    @Autowired
    public SignAspect(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && " +
            "execution(* springfox.documentation..*.*(..))")
    public void swagger() {
    }

    @Pointcut("execution(* org.ltx.hc.business.controller.UserController.addUser(..))")
    public void addUser() {
    }

    @Pointcut("execution(* org.ltx.hc.sys.controller.ApiController.signIn(..))")
    public void signIn() {
    }

    @Pointcut("execution(* org.ltx.hc.sys.controller.ApiController.signOut(..))")
    public void signOut() {
    }

    @Pointcut("execution(* org.ltx.hc.business.controller.UserController.chkId(..))")
    public void chkId() {
    }

    @Pointcut("execution(* org.ltx.hc.business.controller.HdfsController.downloadFromHdfs(..))")
    public void downloadFromHdfs() {
    }

    @Pointcut("execution(* org.ltx.hc.business.controller.HdfsController.uploadToHdfs(..))")
    public void uploadToHdfs() {
    }

    @Pointcut("execution(* org.ltx.hc.business.controller.HdfsController.deleteFromHdfs(..))")
    public void deleteFromHdfs() {

    }

    @Pointcut("execution(* org.ltx.hc.business.controller.HdfsController.getFileList(..))")
    public void getFileList() {
    }

//    @Pointcut("execution(* org.ltx.hc.business.service.)")

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && " +
            "!swagger() && " +
            "!addUser() && " +
            "!signIn() && " +
            "!signOut() && " +
            "!chkId() && " +
            "!downloadFromHdfs() && " +
            "!deleteFromHdfs() && " +
            "!uploadToHdfs() && " +
            "!getFileList() && " +
            "execution(* *(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object cusAround(ProceedingJoinPoint point) throws Throwable {
//        if (DEBUG) {
//            log.info("########testAround########");
//        }
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String reqUrl = request.getServletPath();
        String requestMapping = "/hc";
        if(reqUrl.contains(requestMapping)){
            if(oauthService.getUserInfo() == null){
                throw new UserOauthException();
//                return ApiResult.error("904", "login failed");
            }
        }
        return point.proceed();
    }

//    @Before("controller()")
//    public void cusBefore() throws Throwable {
//        if (DEBUG) {
//            log.info("###########testBefore############");
//        }
//    }
//
//    @After("controller()")
//    public void cusAfter() throws Throwable {
//        if (DEBUG) {
//            log.info("#############testAfter############");
//        }
//    }
//
//    @AfterReturning(returning = "rvt", pointcut = "within(@org.springframework.web.bind.annotation.RestController *) && " +
//            "!swagger() && " +
//            "execution(* *(..))")
//    public Object afterExec(JoinPoint joinPoint, Object rvt) {
//        if (DEBUG) {
//            log.info("############AfterReturning#############");
//        }
//        return rvt;
//    }
}
