package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Category;

public interface CategoryService {
	public Category addCategory(Category category);

	public List<Category> getAllCategory();

	public Category getCategoryById(int categoryID);

	public void removeCategoryById(int categoryId);

}
