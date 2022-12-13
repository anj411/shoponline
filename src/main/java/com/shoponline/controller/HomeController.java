package com.shoponline.controller;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.shoponline.service.MenuService;
import com.shoponline.service.ProductService;
import com.shoponline.service.UserService;

@Controller
public class HomeController 
{
	/*
	 * @Autowired private UserService userService;
	 */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping({"/", "/home"})
	public ModelAndView home()
	{
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home.html");
		modelView.addObject("title", "Home - Online Shopping");
		List<Menu> menu = menuService.getAllMenu();
		modelView.addObject("menu", menu);
		return modelView;
	}
	
	@GetMapping("/about")
	public ModelAndView about()
	{
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("about.html");
		modelView.addObject("title", "About - Online Shopping");
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
	
	@GetMapping("/men/{menuId}")
	public ModelAndView men(@PathVariable int menuId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}
	
	@GetMapping("/women/{menuId}")
	public ModelAndView women(@PathVariable int menuId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}
	
	@GetMapping("/kids/{menuId}")
	public ModelAndView kids(@PathVariable int menuId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
		return modelAndView;
	}
	
	@GetMapping("/home&living/{menuId}")
	public ModelAndView homeliving(@PathVariable int menuId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("category.html");
		Menu menu = menuService.getMenuById(menuId);
		List<Category> category = menu.getCategories();
		modelAndView.addObject("categories", category);
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
	
	@GetMapping("/product/{productId}")
	public ModelAndView getProductById(@PathVariable int productId)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product_detail.html");
		Product product = productService.getProductByProductId(productId);
		modelAndView.addObject("products", product);
		return modelAndView;
	}
	
	@GetMapping("/category/{categoryId}")
	public ModelAndView getCategoryById(@PathVariable int categoryId)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("products.html");
		Category category = categoryService.getCategoryById(categoryId);
		List<Product> prodList = category.getProducts();
		modelAndView.addObject("categories", category);
		modelAndView.addObject("products", prodList);

		return modelAndView;
	}
	@GetMapping("/cart")
	public ModelAndView viewCart() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cart.html");
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("/placeorder")
	public ModelAndView placeorder(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("placeorder.html");
		String username = principal.getName();
		System.out.println("username " + username);
		User user = userService.findUserByUserName(username);
		
		modelAndView.addObject("users", user);
		ShippingAddress shippingAddress = new ShippingAddress();
		modelAndView.addObject("shippingAddress", shippingAddress);
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('User')")
	@PostMapping("/address") 
	public String address(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress, Principal principal) {

		JSONObject json = new JSONObject();

		json.put("name", shippingAddress.getName());
		json.put("mobileNumber", shippingAddress.getMobileNumber());
		json.put("pinCode", shippingAddress.getPinCode());
		json.put("address", shippingAddress.getAddress());
		json.put("city", shippingAddress.getCity());
		json.put("state", shippingAddress.getState());

		String username = principal.getName();
		User user = userService.findUserByUserName(username);
		user.setUserAddress(json.toString());	
		userService.saveUser(user);		  
	  
		return "redirect:/payment";
		 
	}
	
	@PreAuthorize("hasRole('User')")
	  @GetMapping("/payment") 
	  public ModelAndView payment(Principal principal) { 
		  ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("payment.html");
			String username = principal.getName();
			User user = userService.findUserByUserName(username);
				
			JSONParser parser = new JSONParser();
			
			try {
				JSONObject json = (JSONObject) parser.parse(user.getUserAddress());
				 
				 ShippingAddress shippingAddress = new ShippingAddress();
				 
				 shippingAddress.setName(json.get("name").toString());
				 shippingAddress.setMobileNumber(json.get("mobileNumber").toString());
				 shippingAddress.setPinCode(json.get("pinCode").toString());
				 shippingAddress.setAddress(json.get("address").toString());
				 shippingAddress.setCity(json.get("city").toString());
				 shippingAddress.setState(json.get("state").toString());
				 modelAndView.addObject("shipAddress", shippingAddress);
				 
			} catch (ParseException e) {
				e.printStackTrace();
			}  
				   
			return modelAndView;
	  }
	  
	  @GetMapping("/confirmorder")
	  public ModelAndView confirmOrder() {
		  ModelAndView modelAndView = new ModelAndView();
		  modelAndView.setViewName("confirm_order.html");
		  return modelAndView;
	  }
	
}
