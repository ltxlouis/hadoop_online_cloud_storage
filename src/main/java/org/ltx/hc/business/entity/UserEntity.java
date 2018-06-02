package org.ltx.hc.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.io.Serializable;

/**
 * @author ltxlouis
 * @since 3/23/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hc_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 30956207055923645L;

    @Id
    @NotBlank
    private String id;
    private String username;
    @NotBlank
    private String password;
    private String gender;
    @Digits(integer = 100, fraction = 0)
    private int age;
    @Email
    private String email;
    private int isValid;
    private String timeCreated;
    private String timeModified;
}
