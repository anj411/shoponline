package com.shoponline.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dao.CategoryRepository;
import com.shoponline.dto.CategoryDTO;
import com.shoponline.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class.getName());

	public static String uploadCategoryImageDir = System.getProperty("user.dir")
			+ "/src/main/resources/static/categoryImages";

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	MenuService menuService;

	public Category addCategory(CategoryDTO categoryDTO, MultipartFile file, String imgName) throws Exception {
		
		logger.info("adding category with name : "+categoryDTO.getName());
		Category category = new Category();
		
		try {
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
			category = categoryRepository.save(category);
		} catch (Exception ex) {
			logger.error("Exception occurred while saving category. Error : " + ex.getMessage());
		}

		return category;
	}

	public List<Category> getAllCategory() {
		logger.info("getting all categories from DB");
		List<Category> catList = new ArrayList<Category>();
		try {
			catList = categoryRepository.findAll();
		} catch (Exception ex) {
			logger.error("Exception occured while getting Categories list from DB, Error : " + ex.getMessage());
		}
		return catList;
	}

	public Category getCategoryById(int categoryId) {
		
		logger.info("getting category by categoryId from DB");

		Category category = null;
		try {
			category = categoryRepository.findById(categoryId).get();
		} catch (Exception ex) {
			logger.error("Exception occurred while getting category from DB, Error : " + ex.getMessage());
		}
		return category;
	}

	/*
	 * public void removeCategoryById(int categoryId) {
	 * 
	 * logger.info("deleting category for categoryId :"+categoryId+" from DB"); try
	 * { categoryRepository.deleteById(categoryId); } catch (Exception ex) {
	 * logger.error("Exception occurred while deleting category from DB, Error : " +
	 * ex.getMessage()); } }
	 */

	public CategoryDTO updateCategory(int categoryId) {
		
		logger.info("updating Category for category Id :"+categoryId);
		CategoryDTO categoryDTO = new CategoryDTO();

		try {
			Category category = getCategoryById(categoryId);
			categoryDTO.setId(category.getId());
			categoryDTO.setName(category.getName());
			categoryDTO.setMenuId(category.getMenu().getMenuId());
			categoryDTO.setImageName(category.getImageName());
		} catch (Exception ex) {
			logger.error("Exception occurred while updating category from DB, Error : " + ex.getMessage());
		}

		return categoryDTO;
	}
}
