package tictoc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

// http://localhost:8080/swagger-ui/index.html
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TicToc API",
                description = "TicToc API 명세서",
                version = "1.0.0")
)
@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch(
                        "/api/v1/member/login",
                        "/api/v1/member/schedule"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi auctionGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Auction")
                .displayName("Auction")
                .pathsToMatch("/api/v1/member/auction/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bidGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Bid")
                .displayName("Bid")
                .pathsToMatch("/api/v1/member/bid/**")
                .build();
    }
}