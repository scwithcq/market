package com.fmcd.market.Service;

import com.fmcd.market.entity.Products.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllNoDeleteStatusProducts(String keyword);
    Product getProductById(Integer productId);
    int updateProductInfo(Product product);
    int updateProductStatus(Integer productId, String status, Integer operatorId);
    int markProductAsNew(Integer productId, Integer isNew, Integer operatorId);
    List<Product> getNewProducts();
    List<Product> getAdminProductList();
}
