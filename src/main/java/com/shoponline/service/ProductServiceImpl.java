package com.shoponline.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dao.ProductRepository;
import com.shoponline.dto.ProductDTO;
import com.shoponline.model.Menu;
import com.shoponline.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class.getName());
	
	public static String uploadProductImageDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryService categoryService;
	
	public List<Product> getAllProduct() {
		logger.info("getting all product from DB");
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = productRepository.findAll();
			
		} catch (Exception ex) {
			logger.error("Exception occured while getting products list from DB, Error : " + ex.getMessage());
		}
		return productList;
	}

	public Product addProduct(ProductDTO productDTO, MultipartFile file, String imgName) throws Exception{
		logger.info("adding product with name : "+productDTO.getName());
		Product product = new Product();
		
		try {
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
			productRepository.save(product);
			
		} catch (Exception ex) {
			logger.error("Exception occurred while saving product. Error : " + ex.getMessage());
		}
		
		return product;
	}
	
	public void removeProductById(int productId)
	{
		logger.info("deleting product for productId :"+productId+" from DB");
		try {
			productRepository.deleteById(productId);
		} catch (Exception ex) {
			logger.error("Exception occurred while deleting product by productId from DB, Error : " + ex.getMessage());
		}
	}
	
	public Product getProductByProductId(int productId)
	{
		logger.info("getting products by productId from DB : " +productId);
		Product product = null;
		try {	
			 product = productRepository.findById(productId).get();
			 
		}  catch(Exception ex) {
			logger.error("Exception occured while getting product by productId from DB, Error : " + ex.getMessage());
		}
		
		return product;
	}
	
	public List<Product> getAllProductByCategoryId(int categoryId) {
		logger.info("getting products by categoryId from DB : "+categoryId);
		List<Product> product = new ArrayList<Product>();
		try {
			product = productRepository.findAllByCategory_Id(categoryId);
		}  catch(Exception ex) {
			logger.error("Exception occured while getting products by categoryId from DB, Error : " + ex.getMessage());
		}
		return product;
	}
	
	public ProductDTO updateProduct(int id) {
		logger.info("updating products by categoryId from DB : "+id);		
		ProductDTO productDTO = new ProductDTO();
		
		try {
			Product product = getProductByProductId(id);
			productDTO.setId(product.getId());
			productDTO.setName(product.getName());
			productDTO.setCategoryId((product.getCategory().getId()));
			productDTO.setColor(product.getColor());
			productDTO.setDescription(product.getDescription());
			productDTO.setPrice(product.getPrice());
			productDTO.setSize(product.getSize());
			productDTO.setStock(product.getStock());
			productDTO.setImageName(product.getImageName());
			
		}  catch(Exception ex) {
			logger.error("Exception occured while updating products by id from DB, Error : " + ex.getMessage());
		}
			
		return productDTO;
	}

}
