//ShoppingApplication.java
package com.hccs.shopping;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hccs.shopping.dao.ProductRepository;
import com.hccs.shopping.entities.Product;
import com.hccs.shopping.services.ProductService;

@SpringBootApplication
public class ShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initializeDatabase(ProductService service) {
		return args -> service.initializeDatabase();
	}

}
