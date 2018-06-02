package org.ltx.hc.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ltxlouis
 * @since 4/13/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hc_file")
public class FileEntity implements Serializable {

    private static final long serialVersionUID = -1947829331038114966L;

    @Id
    @GeneratedValue
    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    private String parentId;
    @NotBlank
    private String title;
    @NotBlank
    private String origTitle;
    @NotNull
    private Integer lft;
    @NotNull
    private Integer rgt;
    @NotNull
    private Integer nodeLevel;
    @NotNull
    private int isFile;
    private String timeCreated;
    private String timeModified;
}
