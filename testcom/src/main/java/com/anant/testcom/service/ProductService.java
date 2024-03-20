package com.anant.testcom.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anant.testcom.exception.ProductException;
import com.anant.testcom.model.Product;
import com.anant.testcom.request.CreateProduct;



  public interface ProductService {

		public Product createProduct(CreateProduct req);
	
		public String deleteProduct(Long productId) throws ProductException;
	
		public Product updateProduct(long productId, Product erq) throws ProductException;
	
		public Product findProductById(Long id)throws ProductException;
	
		public List<Product> findProductByCategory(String category);
	
		public Page<Product>getAllProduct(String category, List<String>colors, List<String>sizes,Integer minPric,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
}
