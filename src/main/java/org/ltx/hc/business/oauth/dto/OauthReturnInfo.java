package org.ltx.hc.business.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Getter
@Setter
public class OauthReturnInfo implements Serializable {

    private static final long serialVersionUID = -9172896005134172541L;

    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("flag")
    private String flag;
    @JsonProperty("errorInfo")
    private String errorInfo;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("access_token")
    private String accessToken;

}
