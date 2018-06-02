package org.ltx.hc.business.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author ltxlouis
 * @since 4/17/2018
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FileTreeVo implements Serializable {

    private static final long serialVersionUID = 2657594994172801249L;

    private String id;

    private String userId;

    private String parentId;

    private String title;

    private String origTitle;

    private Integer lft;

    private Integer rgt;

    private Integer nodeLevel;

    private int isFile;

    private String timeCreated;

    private String timeModified;

    private List<FileTreeVo> children;

    public void addChild(FileTreeVo fileTreeVo) {
        if (fileTreeVo != null) {
            this.children.add(fileTreeVo);
        }
    }

    public void addChild(List<FileTreeVo> fileTreeVoList) {
        if (fileTreeVoList != null && !fileTreeVoList.isEmpty()) {
            this.children.addAll(fileTreeVoList);
        }
    }

}
