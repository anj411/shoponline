package com.shoponline.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoponline.dto.ProductDTO;
import com.shoponline.model.Product;

public interface ProductService {

    public List<Product> getAllProduct();

    public Product addProduct(ProductDTO productdto, MultipartFile file, String imgName) throws Exception;

    public void removeProductById(int productId);

    public Product getProductByProductId(int productId);

    public List<Product> getAllProductByCategoryId(int categoryId);

}
