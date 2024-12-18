// ProductService.java
package com.hccs.shopping.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hccs.shopping.dao.ProductRepository;
import com.hccs.shopping.entities.Product;

// Traditional three-layer architecture for Java apps:
// - Presentation Layer (controllers and views)
// - Service Layer (business logic and transaction boundaries)
// - Persistence Layer (convert entities to table rows and back)

@Service
@Transactional
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public void initializeDatabase() {
		if (productRepository.count() == 0) {
			productRepository.saveAll(
					List.of(
						new Product("Mixed Alcohol Basket", BigDecimal.valueOf(249.99)),
						new Product("Pearl Necklace", BigDecimal.valueOf(319.99)),
						new Product("Baking Kit Basket", BigDecimal.valueOf(99.99))
						)
				).forEach(System.out::println);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Product> findProductById(Long id) {
		return productRepository.findById(id);
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void deleteProduct(Product p) {
        productRepository.delete(p);
    }
	
	public void deleteAllProducts() {
        productRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByMinPrice(double minPrice) {
        return productRepository.findAllByPriceGreaterThanEqual(BigDecimal.valueOf(minPrice));
    }

    @Transactional(readOnly = true)
    public long countProducts() {
        return productRepository.count();
    }
	
	
}















