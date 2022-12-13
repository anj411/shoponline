package com.shoponline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.dto.CategoryDTO;
import com.shoponline.dto.ProductDTO;
import com.shoponline.model.Category;
import com.shoponline.model.Product;
import com.shoponline.service.CategoryService;
import com.shoponline.service.MenuService;
import com.shoponline.service.ProductService;
import com.shoponline.service.UserService;

@Controller
public class AdminController {

	private Logger log = LoggerFactory.getLogger(AdminController.class.getName());
	
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	MenuService menuService;
	
	@PostConstruct
    public void initMenu() {
        menuService.initMenu();
        
    }

	// Admin Section
	
	
	/**
	 * this api when called returns Admin Home Panel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin")
	public ModelAndView adminHome() {
		log.info("adminHome: api Start");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("admin/adminHome");
		log.info("adminHome: api Stopped");
		return modelView;
	}

	// Menu Section
	/**
	 * This api when called return Menus
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/menus")
	public ModelAndView getMenus() {
		log.info("getMenus api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/menus.html");
		modelView.addObject("menus", menuService.getAllMenu());
		log.info("getMenus api stopped");
        return modelView;
	}

	// Category Section
	/**
	 * This api when called return Category
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories")
	public ModelAndView getCategories() {
		log.info("getCategories: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categories.html");
		modelView.addObject("categories", categoryService.getAllCategory());
		log.info("getCategories: api stopped");
		return modelView;
	}
	
	/**
	 * This api when called returns add category page in admin panel.
	 * @return
	 */
    @PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/add")
	public ModelAndView getAddCategory() {
    	log.info("getAddCategory: api started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", new CategoryDTO());
		modelView.addObject("menus", menuService.getAllMenu());
		log.info("getAddCategory: stopped");
		return modelView;
	}

    /**
	 * This api when called redirects admin to categories Page.
	 * @param categoryDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/categories/add")
	public String postAddCategory(@ModelAttribute("categoryDTO") CategoryDTO categoryDTO, @RequestParam("categoryImage") MultipartFile file,
			@RequestParam("imgName") String imgName) throws Exception{
		log.info("postAddCategory: api started");
		categoryService.addCategory(categoryDTO, file, imgName);
		log.info("postAddCategory: api stopped");
		return "redirect:/admin/categories";
	}

	/**
	 * This api when called redirects admin to category page
	 * after deletion
	 * @param categoryId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/delete/{categoryId}")
	public String deleteCategory(@PathVariable int categoryId) {
		log.info("deleteCategory: api started");
		categoryService.removeCategoryById(categoryId);
		log.info("deleteCategory: api stopped");
		return "redirect:/admin/categories";
	}

	/**
	 * This api when called returns updateCategory Page.
	 * @param categoryId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/update/{categoryId}")
	public ModelAndView updateCategory(@PathVariable int categoryId, Model model) {
		log.info("updateCategory api started");
		ModelAndView modelView = new ModelAndView();
		CategoryDTO categoryDTO = categoryService.updateCategory(categoryId);
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", categoryDTO);
		modelView.addObject("menus", menuService.getAllMenu());
		log.info("updateCategory: api stopped");
		return modelView;
	}

	// Product Section
	
	/**
	 * This api when called returns products page for admin.
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products")
	public ModelAndView getProducts() {
		log.info("getProducts api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/products.html");
		modelView.addObject("products", productService.getAllProduct());
		log.info("getProducts api Stopped");
		return modelView;
	}

	/**
	 * This api when called returns getAddProduct page for admin
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products/add")
	public ModelAndView getAddProduct() {
		log.info("getAddProduct: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/productAdd.html");
		modelView.addObject("productDTO", new ProductDTO());
		modelView.addObject("categories", categoryService.getAllCategory());
		log.info("getAddProduct: api Stopped");
		return modelView;
	}

	/**
	 * This api when called redirects admin to add
	 * products page after adding the product.
	 * @param productDTO
	 * @param file
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/products/add")
	public String postAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws Exception {
		log.info("postAddProduct: api Started");
		productService.addProduct(productDTO, file, imgName);
		log.info("postAddProduct: api Stopped");
		return "redirect:/admin/products";
	}

	/**
	 * This api when called redirects admin to Products page
	 * after deleting the product.
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		log.info("deleteProduct: api Started");
		productService.removeProductById(id);
		log.info("deleteProduct: api stopped");
		return "redirect:/admin/products";
	}

	/**
	 * This api when called return update product page
	 * @param id
	 * @return
	 */	
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/update/{id}")
	public ModelAndView getUpdateProduct(@PathVariable int id) {
		log.info("getUpdateProduct: api Started");
		ModelAndView modelView = new ModelAndView();
		ProductDTO productDTO = productService.updateProduct(id);
		
		modelView.setViewName("/admin/productAdd.html");
		modelView.addObject("productDTO", productDTO);
		modelView.addObject("categories", categoryService.getAllCategory());
		log.info("getUpdateProduct: api Stopped");
		return modelView;
	}
}
