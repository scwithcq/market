package com.fmcd.market.Controllers;

import com.fmcd.market.Entity.Products.Product;
import com.fmcd.market.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 4.1 获取所有商品（用户端）
    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllNoDeleteStatusProducts();
    }

    // 4.2 获取商品详情   用户端/商家端
    @GetMapping("/{productId}")
    public Product getProductDetail(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    // 4.3 商品信息维护  商家端/管理员
    @PatchMapping("/{productId}")
    public String updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        product.setProductId(productId);
        productService.updateProductInfo(product);
        return "商品信息更新成功";
    }

    // 4.4 商品上下架  商家端/管理员
    @PatchMapping("/{productId}/status")
    public String updateStatus(@PathVariable Integer productId, @RequestBody StatusRequest req) {
        productService.updateProductStatus(productId, req.status, req.operatorId);
        return "商品状态已更新";
    }

    // 4.5 新品标记
    @PatchMapping("/{productId}/mark-new")
    public String markNew(@PathVariable Integer productId, @RequestBody MarkNewRequest req) {
        productService.markProductAsNew(productId, req.isNew, req.operatorId);
        return "新品状态已更新";
    }

    // 新品列表
    @GetMapping("/new")
    public List<Product> getNewProducts() {
        return productService.getNewProducts();
    }

    // 4.6 管理端商品列表
    @GetMapping("/admin")
    public List<Product> getAdminProducts() {
        return productService.getAdminProductList();
    }

    // 内部静态类，用于PATCH请求参数映射
    static class StatusRequest {
        public String status;
        public Integer operatorId;
        public String reason;
    }

    static class MarkNewRequest {
        public Integer isNew;
        public Integer operatorId;
    }
}
