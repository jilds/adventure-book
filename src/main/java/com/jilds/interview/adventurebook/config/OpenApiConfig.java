package com.jilds.interview.adventurebook.config;

/*import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;*/

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@RequiredArgsConstructor
public class OpenApiConfig {

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Adventure Book API")
                        .description("Implementation of an Adventure Book REST API")
                        .version(buildProperties.getVersion()));
    }

    private Components getComponents() {
        return new Components()
                .addResponses("500", new ApiResponse().description("Generic service error"))
                .addResponses("501", new ApiResponse().description("When the requested API has not been implemented yet."))
                .addResponses("404", new ApiResponse().description("When no resource are found for the giver criteria."))
                .addResponses("400", new ApiResponse().description("When the request is malformed or any user input is invalid"))
                .addResponses("422", new ApiResponse().description("When the input does not pass validation and is unprocessable."));
    }

}
