package com.fmcd.market.entity.Products;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 商品实体类，对应数据库表 product
 */
@Data
public class Product {
    private Integer productId;        // 商品ID
    private String name;              // 商品名称
    private Integer categoryId;       // 分类ID
    private BigDecimal price;         // 商品价格
    private String unit;              // 计量单位
    private String imageUrl;          // 商品图片URL
    private String rgbJson;           // RGB图像数据(JSON)
    private String status;            // 商品状态(on_sale/off_sale/deleted)
    private Float confidence;         // 识别置信度
    private Integer isRecommend;      // 是否推荐(0=否,1=是)
    private Integer isNew;            // 是否新品(0=否,1=是)
    private Date recommendDate;       // 推荐日期
    private Timestamp createdAt;      // 创建时间
    private Timestamp updatedAt;      // 更新时间
    private Integer creator;          // 创建人ID
    private Integer updator;          // 更新人ID
    private Integer isDeleted;        // 是否删除(0=未删除,1=已删除)
    private Timestamp deletedAt;      // 删除时间
}
