package com.shoponline.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dto.CategoryDTO;
import com.shoponline.model.Category;

/**
 * This is the abstraction for Category Service
 * Operations related to Category such as add Category, delete Category, get Category etc
 * will be called from here.
 */
public interface CategoryService {
	/**
     * this service operation will add the passed category
     * and will return the same category.
     * @param categoryDTO
     * @param file
     * @param imgName
     * @return
     */
	public Category addCategory(CategoryDTO categoryDTO, MultipartFile file, String imgName) throws Exception;

	/**
     * this service operation when called returns the
     * List of Categories for that Admin user.
     * @return
     */
	public List<Category> getAllCategory();

	/**
     * this service operation when called returns
     * Category object corresponding to the passed categoryID.
     * @param categoryID
     * @return
     */
	public Category getCategoryById(int categoryID);

	/**
     * this service operation will delete the category
     * corresponding to the passed categoryID.
     * @param categoryId
     * @return
     
	public void removeCategoryById(int categoryId);*/
	
	/**
     * this service operation will update the category
     * corresponding to the passed categoryID.
     * @param categoryId
     * @return
     */
	public CategoryDTO updateCategory(int categoryId);

}
