/*
 * package com.hccs.shopping.config;
 

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hccs.shopping.dao.ProductRepository;
import com.hccs.shopping.entities.Product;

@Component
public class AppInit implements CommandLineRunner {
	private final ProductRepository productRepository;
	
	public AppInit(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public void run(String... args) {
		if (productRepository.count() == 0) {
			productRepository.saveAll(
					List.of(
						new Product("TV Tray", BigDecimal.valueOf(4.95)),
						new Product("Toaster", BigDecimal.valueOf(19.95)),
						new Product("Sofa", BigDecimal.valueOf(249.95))
						)
				).forEach(System.out::println);
		}
		
	}

}
*/