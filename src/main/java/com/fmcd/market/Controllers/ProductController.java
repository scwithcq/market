package com.fmcd.market.Controllers;

import com.fmcd.market.Common.base.PageData;
import com.fmcd.market.Common.base.Result;
import com.fmcd.market.dto.Products.ProductResponseDTO;
import com.fmcd.market.entity.Products.Product;
import com.fmcd.market.Service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 4.1 获取所有商品（用户端）
/*    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllNoDeleteStatusProducts();
    }*/

    // 4.1 获取所有商品（用户端）   --不包含页码等信息
/*    @GetMapping
    public Result<Map<String, Object>> getProducts() {
        List<Product> products = productService.getAllNoDeleteStatusProducts();
        List<ProductResponseDTO> productResponseDTOS = products.stream().map(product -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                }
        ).collect(Collectors.toList());
        BeanUtils.copyProperties(products, productResponseDTOS);
        HashMap<String, Object> data = new HashMap<>();
        data.put("items", productResponseDTOS);

        return Result.success(data);
    }*/

    // 4.1 获取所有商品（用户端）  可以进行商品名称的模糊查询
    @GetMapping
    public Result<PageData<ProductResponseDTO>> getProducts(@RequestParam(value = "keyword",required = false)String keyword) {
        List<Product> products = productService.getAllNoDeleteStatusProducts(keyword);
        List<ProductResponseDTO> productResponseDTOS = products.stream().map(product -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    BeanUtils.copyProperties(product, dto);
                    return dto;
                }
        ).collect(Collectors.toList());
        Integer total = productResponseDTOS.size(); //列表中元素的数量
        PageData<ProductResponseDTO> pageData = new PageData<>(total, 10, 1, 1, productResponseDTOS);
        return Result.success(pageData);
    }
    // 4.2 获取商品详情   用户端/商家端
    @GetMapping("/{productId}")
    public Result<Map<String, Object>> getProductDetail(@PathVariable Integer productId) {
        Product productDataById = productService.getProductById(productId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("item", productDataById);
        return Result.success(data);
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
