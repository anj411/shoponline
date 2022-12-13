package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Category;
import com.shoponline.model.Menu;
import com.shoponline.model.Product;

public interface HomeService {
	public Category saveCategory(Category category);

	public Menu saveMenu(Menu menu);

	public Product saveProduct(Product product);

	public List<Category> getAllCategories();

	public List<Product> getAllProducts();

	public Category getCategoryById(int categoryId);

	public Product getProductById(int productId);

	public Menu getMenuById(int menuId);
}