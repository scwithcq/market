package com.fmcd.market.dto.Products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Integer productId; //产品id
    private String name; //产品名称
    private BigDecimal price; // 产品价格
    private String unit; // 使用的单位
    private String imageUrl; //产品图片
    private Integer isRecommend;      // 是否推荐(0=否,1=是)
    private Integer isNew;            // 是否新品(0=否,1=是)
}
