package com.kmienko.Book_Social_Network_Tutorial.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kamil Mieńko",
                        url = "https://pl.linkedin.com/in/kamilmienko/en"
                ),
                description = "OpenApi documentation for Spring security",
                title = "OpenApi specification - Kamil Mieńko",
                version = "1.0",
                license = @License(
                        name = "Licence name test",
                        url = "test"
                ),
                termsOfService = "Terms of service - test"
        ),
        servers = {
                @Server(
                        description = "Local enviroment",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                        description = "Production!",
                        url = "https://pl.linkedin.com/in/kamilmienko/en"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
