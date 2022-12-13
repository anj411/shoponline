package com.shoponline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.CategoryRepository;
import com.shoponline.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	MenuService menuService;
	
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	public Category getCategoryById(int categoryId)
	{
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.get();
	}
	
	public void removeCategoryById(int categoryId)
	{
		categoryRepository.deleteById(categoryId);
	}
}
