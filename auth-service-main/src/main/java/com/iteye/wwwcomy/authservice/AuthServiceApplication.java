package com.iteye.wwwcomy.authservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.iteye.wwwcomy.authservice.service.CharPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.LengthPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.NumberPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.PasswordPolicyFilterChain;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSwagger2
@EnableJpaAuditing
public class AuthServiceApplication {
	private final static Logger LOGGER = LoggerFactory.getLogger(AuthServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
		LOGGER.info("Auth Service has started...");
	}

	@Autowired
	private CharPasswordPolicyFilter charFilter;
	@Autowired
	private NumberPasswordPolicyFilter numberFilter;
	@Autowired
	private LengthPasswordPolicyFilter lengthFilter;

	@Bean
	public PasswordPolicyFilterChain createPasswordPolicyFilterChain() {
		PasswordPolicyFilterChain chain = new PasswordPolicyFilterChain();
		chain.getFilters().add(lengthFilter);
		chain.getFilters().add(numberFilter);
		chain.getFilters().add(charFilter);
		return chain;
	}

	@Bean
	public Docket createRestApi() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("token").description("Token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.iteye.wwwcomy.authservice.controller"))
				.paths(PathSelectors.any()).build().globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("wwwcomy", "", "wwwcomy@gmail.com");
		return new ApiInfoBuilder().title("AuthService").description("Authentication & Authorization Service")
				.contact(contact).version("1.0").build();
	}

}
