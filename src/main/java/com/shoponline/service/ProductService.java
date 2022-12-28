package com.shoponline.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dto.ProductDTO;
import com.shoponline.model.Product;

/**
 * This is the abstraction for Product Service
 * Operations related to Products such as add Product, delete Product, get Products, etc
 * will be called from here.
 */
public interface ProductService {

	/**
     * this service operation when called returns the
     * List of Products for that Admin user.
     * @return
     */
    public List<Product> getAllProduct();

    /**
     * this service operation will add the passed product
     * and will return the same product.
     * @param product
     * @param file
     * @param imgName
     * @return
     */
    public Product addProduct(ProductDTO productdto, MultipartFile file, String imgName) throws Exception;

    /**
     * this service operation when called will delete
     * the product corresponding to the passed productID
     * @param productId
     * @return
     */
    public void removeProductById(int productId);

    /**
     * this service operation when called returns the
     * Product corresponding to the passed productId
     * @param productId
     * @return
     */
    public Product getProductByProductId(int productId);

    /**
     * this service operation when called returns the
     * List of Products under the passed categoryID.
     * @param categoryId
     * @return
     */
    public List<Product> getAllProductByCategoryId(int categoryId);
    
    /**
     * this service operation will update the product
     * corresponding to the passed productID.
     * @param productId
     * @return
     */
    public ProductDTO updateProduct(int productId);

}
