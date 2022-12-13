/*
 * package com.shoponline.controller;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * import com.shoponline.model.Category; import com.shoponline.model.Menu;
 * import com.shoponline.model.Product; import
 * com.shoponline.service.CategoryService; import
 * com.shoponline.service.MenuService; import
 * com.shoponline.service.ProductService;
 * 
 * @Controller
 * 
 * @RequestMapping("/home") //public class HomePageController {
 * 
 * @Autowired private MenuService menuService;
 * 
 * @Autowired private CategoryService categoryService;
 * 
 * @Autowired private ProductService productService;
 * 
 * @RequestMapping(value = "/menu", method = RequestMethod.POST, consumes =
 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Menu>
 * saveMenu(@RequestBody Menu menu) { return new
 * ResponseEntity<Menu>(menuService.addMenu(menu), HttpStatus.CREATED); }
 * 
 * @RequestMapping(value = "/category", method = RequestMethod.POST, consumes =
 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
 * saveCategory(@RequestBody Category category) { return new
 * ResponseEntity<Category>(categoryService.addCategory(category),
 * HttpStatus.CREATED); }
 * 
 * @RequestMapping(value = "/product", method = RequestMethod.POST, consumes =
 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
 * saveProduct(@RequestBody Product product) { return new
 * ResponseEntity<Product>(productService.addProduct(product),
 * HttpStatus.CREATED); }
 * 
 * 
 * //Post Apis
 * 
 * @RequestMapping(value = "/menu", method = RequestMethod.POST, consumes =
 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Menu>
 * createMenu(@RequestBody Menu menu) { this.menuRepository.save(menu); return
 * ResponseEntity.ok(menu); }
 * 
 * @PostMapping("/category") public ResponseEntity<?>
 * createCategory(@RequestBody Category category) {
 * this.categoryRepository.save(category); return
 * ResponseEntity.ok(category.getId()); }
 * 
 * @PostMapping("/product") public ResponseEntity<?> createProduct(@RequestBody
 * Product product) { Product prod = new Product(); try {
 * productRepository.save(product); prod =
 * productRepository.findById(product.getId()).get(); } catch(Exception e) {
 * System.out.println("Error : " + e.getMessage()); }
 * 
 * return ResponseEntity.ok(prod.getId()); }
 * 
 * 
 * // Get apis
 * 
 * @GetMapping("/product/{productId}") public ResponseEntity<Product>
 * getProduct(@PathVariable int productId) { Product product =
 * productService.getProductByProductId(productId); return
 * ResponseEntity.ok(product); }
 * 
 * @RequestMapping(value = {"/category/{categoryId}", "/category"}, method =
 * RequestMethod.GET) public ResponseEntity<?> getCategory(@PathVariable()
 * Optional<Integer> categoryId) throws Exception { List<Category> categoryList
 * = categoryService.getAllCategory();
 * 
 * try { if(categoryId.isPresent()) { int id = categoryId.get() - 1 ; return
 * ResponseEntity.ok().body(categoryList.get(id)); } else { return
 * ResponseEntity.ok().body(categoryList); } } catch(Exception e) {
 * System.out.println("Error : " + e.getStackTrace()); return
 * ResponseEntity.badRequest().body(null); } }
 * 
 * @RequestMapping(value = {"/menu/{menuId}", "/menu"}, method =
 * RequestMethod.GET) public ResponseEntity<?> getMenu(@PathVariable
 * Optional<Integer> menuId) { List<Menu> menuList = menuService.getAllMenu();
 * try { if(menuId.isPresent()) { int id = menuId.get() - 1 ; return
 * ResponseEntity.ok().body(menuList.get(id)); } else { return
 * ResponseEntity.ok().body(menuList); } } catch(Exception e) {
 * System.out.println("Error : " + e.getMessage()); return
 * ResponseEntity.badRequest().body(null); } }
 * 
 * 
 * 
 * }
 */