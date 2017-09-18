package com.toulios.yotirobbot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Configuration for Swagger
 *
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = YotiRobbotApplication.class)
public class SwaggerConfig {

	@SuppressWarnings("unchecked")
	@Bean
	public Docket swagger2Configuration() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("Toulios Exercise")
						.description("This api is for a test exercise.")
						.contact("toulios90@gmail.com")
						.build())
				.select()
				.apis(Predicates.or(
						RequestHandlerSelectors.basePackage("com.toulios.yoti")))
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(LocalDateTime.class, String.class)
				.directModelSubstitute(ZonedDateTime.class, String.class)
				.useDefaultResponseMessages(false);
	}
}

