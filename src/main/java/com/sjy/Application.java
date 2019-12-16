/**
 * 
 */
package com.sjy;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import com.sjy.config.DefaultProfileUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月8日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Slf4j
@EnableEurekaClient
@ServletComponentScan
@ComponentScan
@ImportResource(locations = "classpath:${spring.dubbo.file}")
@SpringBootApplication
public class Application {
	/**
	 * 注入sessionfatory
	 * 
	 * @return
	 */
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(Application.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String contextPath = "";
		if (env.getProperty("server.contextPath") != null) {
			contextPath = env.getProperty("server.contextPath");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), contextPath,
				protocol, InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), contextPath,
				env.getActiveProfiles());

		String configServerStatus = env.getProperty("configserver.status");
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
	}
}
