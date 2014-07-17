package org.springframework.social.skplanetx.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 17.
 */
@EnableAutoConfiguration
@ComponentScan("org.springframework.social.skplanetx.sample")
public class SkplanetxSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkplanetxSampleApplication.class, args);
    }
}
