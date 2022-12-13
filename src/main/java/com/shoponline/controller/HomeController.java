package com.shoponline.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.model.Category;
import com.shoponline.model.Menu;
import com.shoponline.model.Product;
import com.shoponline.model.ShippingAddress;
import com.shoponline.model.User;
import com.shoponline.service.CategoryService;
import com.shoponline.service.HomeService;
import com.shoponline.service.MenuService;
import com.shoponline.service.ProductService;
import com.shoponline.service.UserService;

@Controller
public class HomeController 
{
	private Logger log = LoggerFactory.getLogger(HomeController.class.getName());
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * This api when called returns Home Page.
	 * @return
	 */
	@GetMapping({"/", "/home"})
	public ModelAndView home()
	{
		log.info("home: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home.html");
		modelView.addObject("title", "Home - Online Shopping");
		List<Menu> menu = menuService.getAllMenu();
		modelView.addObject("menu", menu);
		log.info("home: api Stopped");
		return modelView;
	}
	
	/**
	 * This api when called returns about web page.
	 * @return
	 */
	@GetMapping("/about")
	public ModelAndView about()
	{
		log.info("about: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("about.html");
		modelView.addObject("title", "About - Online Shopping");
		log.info("about: api Stopped");
		return modelView;
	}

	/*
	 * @PostMapping("/do_register") public ModelAndView
	 * registerUser(@ModelAttribute("user") User user,
	 * 
	 * @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
	 * HttpSession session) { ModelAndView modelView = new ModelAndView();
	 * modelView.setViewName("register.html");
	 * 
	 * try { if (!agreement) {
	 * System.out.println("You have not accepted terms and conditions."); throw new
	 * Exception("You have not accepted terms and conditions."); }
	 * 
	 * user.setRole("USER");
	 * user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
	 * 
	 * userService.save(user);
	 * 
	 * modelView.addObject("user", new User()); session.setAttribute("message", new
	 * Message("Successfully registered !!", "alert-success"));
	 * 
	 * return modelView; } catch (Exception e) { e.printStackTrace();
	 * modelView.addObject("user", user); session.setAttribute("message", new
	 * Message("Something went wrong !! " + e.getMessage(), "alert-danger")); return
	 * modelView; } }
	 */

	/*
	 * @RequestMapping("/signin") public ModelAndView login() { ModelAndView
	 * modelView = new ModelAndView(); modelView.setViewName("login.html");
	 * modelView.addObject("title", "Login - Online Shopping"); return modelView; }
	 */
	
	/**
	 * This api when called returns Menus page.
	 * @return
	 */
	@GetMapping("/men/{menuId}")
	public ModelAndView men(@PathVariable int menuId) {
		log.info("men: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		log.info("men: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns women category Page.
	 * @param menuId
	 * @return
	 */
	@GetMapping("/women/{menuId}")
	public ModelAndView women(@PathVariable int menuId) {
		log.info("women: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		log.info("women: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns kids category Page.
	 * @param menuId
	 * @return
	 */
	@GetMapping("/kids/{menuId}")
	public ModelAndView kids(@PathVariable int menuId) {
		log.info("kids: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		log.info("kids: api stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns homeLiving category Page.
	 * @param menuId
	 * @return
	 */
	@GetMapping("/home&living/{menuId}")
	public ModelAndView homeliving(@PathVariable int menuId) {
		log.info("homeLiving: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		log.info("homeLiving: api Stopped");
		return modelAndView;
	}
	
	/*
	 * @GetMapping("/{menuId}") public ModelAndView menu(@PathVariable int menuId) {
	 * ModelAndView modelView = new ModelAndView();
	 * modelView.setViewName("category.html"); Menu menu =
	 * menuService.getMenuById(menuId); List<Category> category =
	 * menu.getCategories(); List<Product> products =
	 * productService.getAllProduct(); modelView.addObject("categories", category);
	 * modelView.addObject("products", products); modelView.addObject("menu", menu);
	 * return modelView; }
	 */
	
	/**
	 * This api when called returns selected product's page.
	 * @param productId
	 * @return
	 */
	@GetMapping("/product/{productId}")
	public ModelAndView getProductById(@PathVariable int productId)
	{
		log.info("getProductById: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product_detail.html");
		Product product = productService.getProductByProductId(productId);
		modelAndView.addObject("products", product);
		log.info("getProductById: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns Selected category's page.
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}")
	public ModelAndView getCategoryById(@PathVariable int categoryId)
	{
		log.info("getCategoryById: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("products.html");
		Category category = categoryService.getCategoryById(categoryId);
		List<Product> prodList = category.getProducts();
		modelAndView.addObject("categories", category);
		modelAndView.addObject("products", prodList);
		log.info("getCategoryById: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns cart page.
	 * @return
	 */
	@GetMapping("/cart")
	public ModelAndView viewCart() {
		log.info("viewCart: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cart.html");
		log.info("viewCart: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called returns place order page.
	 * @param principal
	 * @return
	 */
	@PreAuthorize("hasRole('User')")
	@GetMapping("/placeorder")
	public ModelAndView placeorder(Principal principal) {
		
		log.info("placeOrder: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("placeorder.html");
		String username = principal.getName();
		System.out.println("username " + username);
		User user = userService.findUserByUserName(username);
		
		modelAndView.addObject("users", user);
		ShippingAddress shippingAddress = new ShippingAddress();
		modelAndView.addObject("shippingAddress", shippingAddress);
		log.info("placeOrder: api Stopped");
		return modelAndView;
	}
	
	/**
	 * This api when called saves user's address.
	 * @param shippingAddress
	 * @param principal
	 * @return
	 */
	@PreAuthorize("hasRole('User')")
	@PostMapping("/address") 
	public String address(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress, Principal principal) {

		log.info("address: api Started");
		homeService.postAddress(shippingAddress, principal);
		log.info("address: api Stopped");
		return "redirect:/payment";
		 
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("/payment")
	public ModelAndView payment(Principal principal) {
		log.info("payment: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("payment.html");
		ShippingAddress shippingAddress = homeService.payment(principal);
		modelAndView.addObject("shipAddress", shippingAddress);
		return modelAndView;
	}

	@GetMapping("/confirmorder")
	public ModelAndView confirmOrder() {
		log.info("confirmOrder: api Started");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("confirm_order.html");
		log.info("confirmOrder: api Started");
		return modelAndView;
	}
	
}
