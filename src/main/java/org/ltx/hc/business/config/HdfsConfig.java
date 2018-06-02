package org.ltx.hc.business.config;

import org.apache.hadoop.conf.Configuration;
import org.ltx.hc.business.util.HdfsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @author ltxlouis
 * @since 4/3/2018
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HdfsProperties.class)
public class HdfsConfig {

    private HdfsProperties hdfsProperties;

    @Autowired
    public HdfsConfig(HdfsProperties hdfsProperties) {
        this.hdfsProperties = hdfsProperties;
    }

    @Bean
    public HdfsUtil hdfsUtil() {
        return new HdfsUtil(configuration());
    }

    @Bean
    public Configuration configuration() {
        Configuration conf = new Configuration();
        conf.set(hdfsProperties.getDefaultfs(), hdfsProperties.getHost());
        return conf;
    }

}
