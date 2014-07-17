package org.springframework.social.skplanetx.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.skplanetx.api.Skplanetx;
import org.springframework.social.skplanetx.connect.SkplanetxConnectionFactory;
import org.springframework.social.skplanetx.connect.SkplanetxSignInController;
import org.springframework.social.skplanetx.sample.user.SecurityContext;
import org.springframework.social.skplanetx.sample.user.SimpleConnectionSignUp;
import org.springframework.social.skplanetx.sample.user.SimpleSignInAdapter;

import javax.sql.DataSource;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 15.
 *
 * ConnectSupport -> SkplanetxConnectSupport 로 수정
 * 마지막 access_token 을 받을때 scope을 같이 parameter로 넘겨야 되는 문제때문에 확장함.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new SkplanetxConnectionFactory(env.getProperty("skplanetx.clientId"), env.getProperty("skplanetx.clientSecret"), env.getProperty("skplanetx.appKey")));
	}


	/**
	 * Singleton data access object providing access to connections across all users.
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(new SimpleConnectionSignUp());
		return repository;
	}
	
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {
			@Override
			public String getUserId() {
				return SecurityContext.getCurrentUser().getId();
			}
		};
	}

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    public Skplanetx skplanetx(ConnectionRepository repository) {
        Connection<Skplanetx> connection = repository.findPrimaryConnection(Skplanetx.class);
        return connection != null ? connection.getApi() : null;
    }


    /**
     * 필수로 ProviderSignInController 에 SkplanetxSignInController 를 선언해줘야 한다.
     * Issue) access_token 을 받을때도 scope 을 또 한번 넘겨줘야 하는 이슈 때문.
     *
     * @param connectionFactoryLocator
     * @param usersConnectionRepository
     * @return
     */
//	@Bean
//	public ProviderSignInController skplanetxSingInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
//		return new SkplanetxSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter());
//	}

    @Bean
    public SimpleConnectionSignUp simpleConnectionSignUp() {
        return new SimpleConnectionSignUp();
    }

    @Bean
    public SimpleSignInAdapter simpleSignInAdapter() {
        return new SimpleSignInAdapter();
    }

}