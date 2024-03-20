package com.anant.testcom.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anant.testcom.exception.ProductException;
import com.anant.testcom.model.Category;
import com.anant.testcom.model.Product;
import com.anant.testcom.repository.CategoryRepository;
import com.anant.testcom.repository.ProductRepository;
import com.anant.testcom.request.CreateProduct;


@Service
public class ProductServiceImplementation implements ProductService{

	private ProductRepository productRepository;

	private CategoryRepository categoryRepository;
	
	
	
	public ProductServiceImplementation(ProductRepository productRepository,
			CategoryRepository categoryRepository) {
		this.productRepository = productRepository;

		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product createProduct(CreateProduct req) {
		
		Category topLevel= categoryRepository.findByName(req.getTopLevelCategory());
		if(topLevel==null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			topLevel = categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel = categoryRepository
				.findByNameAndParent(req.getSecLevelCategory(), topLevel.getName());
		
		if(secondLevel==null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			
			secondLevel= categoryRepository.save(secondLevelCategory);
		}
		
		Category thirdLevel= categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		
		if(thirdLevel==null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			
			thirdLevel= categoryRepository.save(thirdLevelCategory);
		}
		
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountedPercent(req.getDiscountedPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = productRepository.save(product);
		System.out.println("products - "+product);
		
		return savedProduct;
		
		
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		
		Product product = findProductById(productId);
		System.out.println("delete product "+product.getId()+"- "+productId);
		product.getSizes().clear();
		productRepository.delete(product);
		
		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(long productId, Product req) throws ProductException {
		
		Product product = findProductById(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt = productRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id - "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPric,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		List<Product> products = productRepository.filterProducts(category, minPric, maxPrice, minDiscount, sort);
		
		if(!colors.isEmpty()) {
			products = products.stream()
					.filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
		
		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}else if(stock.equals("out_of_stock")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(),products.size());
		
		List<Product> pageContent = products.subList(endIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
		
		return filteredProducts;
	}

}
