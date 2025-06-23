package com.inventario;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EntityScan(basePackages = "com.inventario.model")
public class InventarioApplication {

	@Autowired
    private DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}
	
	@PostConstruct
    public void verificarConexion() throws Exception {
        System.out.println("📡 Conectado a: " + dataSource.getConnection().getMetaData().getURL());
    }

}
