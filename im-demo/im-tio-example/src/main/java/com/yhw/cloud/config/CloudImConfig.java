package com.yhw.cloud.config;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by YHW on 2019/6/22.
 */
@Getter
@Configuration
public class CloudImConfig implements InitializingBean {

    private static CloudImConfig cloudImConfig = null;

    public static CloudImConfig getInstance(){
        return cloudImConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cloudImConfig = this;
    }
}
