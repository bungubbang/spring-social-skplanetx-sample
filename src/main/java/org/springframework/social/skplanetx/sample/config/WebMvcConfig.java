package org.springframework.social.skplanetx.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.skplanetx.sample.user.UserInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 15.
 *
 * ConnectSupport -> SkplanetxConnectSupport 로 수정
 * 마지막 access_token 을 받을때 scope을 같이 parameter로 넘겨야 되는 문제때문에 확장함.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.springframework.social.skplanetx")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserInterceptor(usersConnectionRepository));
	}

}
