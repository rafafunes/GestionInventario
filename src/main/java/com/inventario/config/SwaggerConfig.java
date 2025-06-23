package com.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Inventario y Ordenes de Compra")
                        .version("1.0.0")
                        .description("Documentación de la API para gestión de proveedores, productos, inventario y órdenes de compra."));
    }
}
