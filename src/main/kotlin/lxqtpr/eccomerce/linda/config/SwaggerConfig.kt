package lxqtpr.eccomerce.linda.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI =
         OpenAPI()
            .info(
                Info()
                    .title("Linda Market Api")
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .email("lxqtpr@gmail.com")
                            .name("Gadelshin Daniil")
                    )
            )
    }