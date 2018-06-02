package org.ltx.hc.business.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ltxlouis
 * @since 4/3/2018
 */
@ConfigurationProperties(prefix = "hdfs", ignoreUnknownFields = false)
@Getter
@Setter
public class HdfsProperties {
    private String defaultfs;
    private String host;
    private String uploadPath;
}
