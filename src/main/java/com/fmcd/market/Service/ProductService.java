package com.fmcd.market.Service;

import com.fmcd.market.Entity.Products.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllNoDeleteStatusProducts();
    Product getProductById(Integer productId);
    int updateProductInfo(Product product);
    int updateProductStatus(Integer productId, String status, Integer operatorId);
    int markProductAsNew(Integer productId, Integer isNew, Integer operatorId);
    List<Product> getNewProducts();
    List<Product> getAdminProductList();
}
