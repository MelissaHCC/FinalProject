// ProductRestController.java
package com.hccs.shopping.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.hccs.shopping.entities.Product;
import com.hccs.shopping.services.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductRestContoller {
	
	private final ProductService service;
	
	public ProductRestContoller(ProductService service) {
		this.service = service;
	}
	
	@GetMapping
	@ResponseBody
	public List<Product> getProducts() {
		return service.findAllProducts();
	}
	
	@GetMapping("{id}")
	@ResponseBody
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		return ResponseEntity.of(service.findProductById(id));
	}
	
	@GetMapping(params = "min")
	@ResponseBody
    public List<Product> getProductsByMinPrice(@RequestParam(defaultValue = "0.0") double min) {
        if (min < 0) throw new ProductMinimumPriceException(min);
        return service.findAllProductsByMinPrice(min);
    }
	
	@GetMapping("count")
	@ResponseBody
    public long getProductCount() {
        return service.countProducts();
    }
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Product> createProduct(@RequestParam String name, @RequestParam BigDecimal price) {
	    Product product = new Product(name, price);
	    Product savedProduct = service.saveProduct(product);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
	            .path("/{id}")
	            .buildAndExpand(savedProduct.getId())
	            .toUri();
	    return ResponseEntity.created(location).body(savedProduct);
	}

	
	/*
	@PostMapping
	@ResponseBody
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product p = service.saveProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(p.getId())
				.toUri();
		return ResponseEntity.created(location).body(p);
	}
	*/
	
	@PutMapping("{id}")
	@ResponseBody
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam String name, @RequestParam BigDecimal price) {
	    return service.findProductById(id)
	            .map(p -> {
	                p.setName(name);
	                p.setPrice(price);
	                return ResponseEntity.ok(service.saveProduct(p));
	            })
	            .orElse(ResponseEntity.notFound().build());
	}

	
	/*
	@PutMapping("{id}")
	@ResponseBody
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
		return service.findProductById(id)
				.map(p -> {
					p.setName(product.getName());
					p.setPrice(product.getPrice());
					return ResponseEntity.ok(service.saveProduct(p));
				})
				.orElse(ResponseEntity.notFound().build());
	}
	*/
	
	
	@DeleteMapping("{id}")
	@ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return service.findProductById(id)
                .map(p -> {
                    service.deleteProduct(p);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping
	@ResponseBody
	public ResponseEntity<?> deleteAllProducts() {
		service.deleteAllProducts();
		return ResponseEntity.noContent().build();
	}
	
	 // New Method for Rendering HTML
    @GetMapping("/html")
    public String viewProductsAsHtml(Model model) {
        model.addAttribute("products", service.findAllProducts());
        return "products"; // Refers to src/main/resources/templates/products.html
    }
	
}