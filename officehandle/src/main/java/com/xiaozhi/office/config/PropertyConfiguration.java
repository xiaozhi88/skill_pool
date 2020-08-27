package com.xiaozhi.office.config;

import com.xiaozhi.office.property.WordProductProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 各个配置文件类开启配置类
 *
 * @author LeoRmAo
 * @date 20190522 10:10:21
 * @since v1.0
 */
@Configuration
@EnableConfigurationProperties({ WordProductProperty.class})
public class PropertyConfiguration {

}
