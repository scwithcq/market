package com.fmcd.market.Mapper;

import com.fmcd.market.Entity.Products.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 获取所有商品（非删除）
    List<Product> getAllNoDeleteStatusProducts();

    // 获取商品详情
    Product getProductById(@Param("productId") Integer productId);

    // 获取所有商品，包括删除 用来在修改已删除订单信息的时候可以检查是否有这笔已删除status
    List<Product> getAllProducts();


    // 更新商品信息
    int updateProductInfo(Product product);

    // 商品上下架
    int updateProductStatus(
            @Param("productId") Integer productId,
            @Param("status") String status,
            @Param("operatorId") Integer operatorId
    );

    // 新品标记
    int markProductAsNew(@Param("productId") Integer productId,
                         @Param("isNew") Integer isNew,
                         @Param("operatorId") Integer operatorId);

    // 查询新品列表
    @Select("""
        SELECT * FROM Products
        WHERE (is_new = 1 OR DATE(created_at) = CURDATE())
          AND status = 'on_sale'
          AND is_deleted = 0
        ORDER BY created_at DESC
        LIMIT 20
    """)
    List<Product> getNewProducts();

    // 管理端商品列表（含软删除）
    @Select("SELECT * FROM Products ORDER BY updated_at DESC")
    List<Product> getAdminProductList();
}
