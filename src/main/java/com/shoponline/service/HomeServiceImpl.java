package com.shoponline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.CategoryRepository;
import com.shoponline.dao.MenuRepository;
import com.shoponline.dao.ProductRepository;
import com.shoponline.model.Category;
import com.shoponline.model.Menu;
import com.shoponline.model.Product;

@Service
public class HomeServiceImpl implements HomeService 
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Menu saveMenu(Menu menu) {
		return menuRepository.save(menu);
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public List<Product> getAllproducts() {
		return productRepository.findAll();
	}
	
	public Category getCategoryById(int categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.get();
	}

	public Product getProductById(int productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product.get();
	}

	public Menu getMenuById(int menuId) {
		Optional<Menu> menu = menuRepository.findById(menuId);
		return menu.get();
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}


}
