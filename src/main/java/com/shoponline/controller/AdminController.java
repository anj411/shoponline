package com.shoponline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

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

	public static String uploadProductImageDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	public static String uploadCategoryImageDir = System.getProperty("user.dir") + "/src/main/resources/static/categoryImages";
	
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
	
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin")
	public ModelAndView adminHome() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("admin/adminHome");
		return modelView;
	}

	// Menu Section
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/menus")
	public ModelAndView getMenus() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/menus.html");
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}

	// Category Section
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories")
	public ModelAndView getCategories() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categories.html");
		modelView.addObject("categories", categoryService.getAllCategory());
		return modelView;
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/add")
	public ModelAndView getAddCategory() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", new CategoryDTO());
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/categories/add")
	public String postAddCategory(@ModelAttribute("categoryDTO") CategoryDTO categoryDTO, @RequestParam("categoryImage") MultipartFile file,
			@RequestParam("imgName") String imgName) throws IOException{
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
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/delete/{categoryId}")
	public String deleteCategory(@PathVariable int categoryId) {
		categoryService.removeCategoryById(categoryId);
		return "redirect:/admin/categories";
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/categories/update/{categoryId}")
	public ModelAndView updateCategory(@PathVariable int categoryId, Model model) {
		ModelAndView modelView = new ModelAndView();
		Category category = categoryService.getCategoryById(categoryId);
		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setMenuId(category.getMenu().getMenuId());
		categoryDTO.setImageName(category.getImageName());
		modelView.setViewName("/admin/categoriesAdd.html");
		modelView.addObject("categoryDTO", categoryDTO);
		modelView.addObject("menus", menuService.getAllMenu());
		return modelView;
	}

	// Product Section
	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products")
	public ModelAndView getProducts() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/products.html");
		modelView.addObject("products", productService.getAllProduct());
		return modelView;
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/products/add")
	public ModelAndView getAddProduct() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("/admin/productAdd.html");
		modelView.addObject("productDTO", new ProductDTO());
		modelView.addObject("categories", categoryService.getAllCategory());
		return modelView;
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@PostMapping("/admin/products/add")
	public String postAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws Exception {
		productService.addProduct(productDTO, file, imgName);

		return "redirect:/admin/products";
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/admin/product/update/{id}")
	public ModelAndView getUpdateProduct(@PathVariable int id) {
		ModelAndView modelView = new ModelAndView();
		Product product = productService.getProductByProductId(id);
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

		modelView.setViewName("/admin/productAdd.html");

		modelView.addObject("productDTO", productDTO);
		modelView.addObject("categories", categoryService.getAllCategory());

		return modelView;
	}
}
