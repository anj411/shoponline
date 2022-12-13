package com.shoponline.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dao.CategoryRepository;
import com.shoponline.dto.CategoryDTO;
import com.shoponline.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	public static String uploadCategoryImageDir = System.getProperty("user.dir") + "/src/main/resources/static/categoryImages";
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	MenuService menuService;
	
	public Category addCategory(CategoryDTO categoryDTO, MultipartFile file, String imgName) throws Exception {
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setMenu(menuService.getMenuById(categoryDTO.getMenuId()));
		String imageUUID;

		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadCategoryImageDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		
		category.setImageName(imageUUID);
		
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
	
	public CategoryDTO updateCategory(int categoryId) {
		Category category = getCategoryById(categoryId);
		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setMenuId(category.getMenu().getMenuId());
		categoryDTO.setImageName(category.getImageName());
		
		return categoryDTO;
	}
}
