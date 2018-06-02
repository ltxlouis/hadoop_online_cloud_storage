package org.ltx.hc.sys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ltxlouis
 * @since 4/19/2018
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "invalid user token")
public class UserOauthException extends RuntimeException {

    private static final long serialVersionUID = 5256648087457454490L;

    public UserOauthException(String userId) {
        super("cannot valid user" + userId + "plz retry.");
    }

    public UserOauthException() {
        super("user token is not valid, plz try again.");
    }

}
