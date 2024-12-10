package com.hccs.shopping.entities;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@SpringBootTest
class ProductTest {

	@Autowired
	private Validator validator;
	
	@Test
	void validProduct() {
		Product product = new Product("TV tray", BigDecimal.valueOf(10.00));
		var violations = validator.validate(product);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void invalidProduct() {
		Product product = new Product(" ", BigDecimal.valueOf(-10.00));
		var violations = validator.validate(product);
		assertEquals(2, violations.size());
		violations.forEach(System.out::println);
	}

}
