package com.shoponline.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dao.ProductRepository;
import com.shoponline.dto.ProductDTO;
import com.shoponline.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	public static String uploadProductImageDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryService categoryService;
	
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	public Product addProduct(ProductDTO productDTO, MultipartFile file, String imgName) throws Exception{
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
		product.setColor(productDTO.getColor());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setSize(productDTO.getSize());
		product.setStock(productDTO.getStock());
		String imageUUID;

		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadProductImageDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		return productRepository.save(product);
	}
	
	public void removeProductById(int productId)
	{
		productRepository.deleteById(productId);
	}
	
	public Product getProductByProductId(int productId)
	{
		Optional<Product> product = productRepository.findById(productId);
		return product.get();
	}
	
	public List<Product> getAllProductByCategoryId(int categoryId) {
		return productRepository.findAllByCategory_Id(categoryId);
	}
	
	public ProductDTO updateProduct(int id) {
		Product product = getProductByProductId(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setColor(product.getColor());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setSize(product.getSize());
		productDTO.setStock(product.getStock());
		productDTO.setImageName(product.getImageName());
		
		return productDTO;
	}

}
