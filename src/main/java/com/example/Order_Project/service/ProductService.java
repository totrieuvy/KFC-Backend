package com.example.Order_Project.service;

import com.example.Order_Project.dto.request.ProductRequest;
import com.example.Order_Project.dto.response.ProductResponse;
import com.example.Order_Project.entity.Category;
import com.example.Order_Project.entity.Product;
import com.example.Order_Project.repository.CategoryRepository;
import com.example.Order_Project.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getPrice(),
                        product.getActive(),
                        product.getCategory()
                ))
                .toList();
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setActive(true);

        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getImage(),
                savedProduct.getPrice(),
                savedProduct.getActive(),
                savedProduct.getCategory()
        );
    }

}
