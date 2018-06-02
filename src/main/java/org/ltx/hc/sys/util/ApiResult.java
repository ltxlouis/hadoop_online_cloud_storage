package org.ltx.hc.sys.util;

import lombok.*;

import java.io.Serializable;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResult implements Serializable {

    public static ApiResult ok(Object result) {
        return new ApiResult("T", null, null, result);
    }

    public static ApiResult error(String errorCode, String errorInfo) {
        return new ApiResult("F", errorCode, errorInfo, null);
    }

    private static final long serialVersionUID = 6002497490312536982L;

    private String flag;

    private String errorCode;

    private String errorInfo;

    private Object result;

}
