package org.ltx.hc.business.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@Builder
@Getter
@Setter
public class UserVo {
    private String id;
    private String username;
    private String gender;
    private int age;
    private String email;
    private int isValid;
    private String timeCreated;
    private String timeModified;
}
