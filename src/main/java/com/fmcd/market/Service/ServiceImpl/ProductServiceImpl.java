package com.fmcd.market.Service.ServiceImpl;

import com.fmcd.market.Entity.Products.Product;
import com.fmcd.market.Mapper.ProductMapper;
import com.fmcd.market.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    // 注入mqtt
//    @Autowired
//    private MessageChannel outboundChannel;

    @Override
    public List<Product> getAllNoDeleteStatusProducts() {
        return productMapper.getAllNoDeleteStatusProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        // 测试
        // 当调用这个api的时候，进行一次发送信息请求
//        send("收到，信息已发送");
        return productMapper.getProductById(productId);
    }

    @Override
    public int updateProductInfo(Product product) {
        if (product.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("价格必须大于0");
        }
        return productMapper.updateProductInfo(product);
    }

    @Override
    public int updateProductStatus(Integer productId, String status, Integer operatorId) {

        // ✅ Step 1：检查是否存在该商品
        List<Product> allProducts = productMapper.getAllProducts();
        if (allProducts.toArray().length == 0) {
            // 可以选择抛出异常或返回 0（让 Controller 判断返回信息）
            throw new IllegalArgumentException("更新失败：未找到对应的商品ID " + productId);
        }

        // ✅ Step 2：执行更新操作
        return productMapper.updateProductStatus(productId, status, operatorId);

    }

    @Override
    public int markProductAsNew(Integer productId, Integer isNew, Integer operatorId) {
        return productMapper.markProductAsNew(productId, isNew, operatorId);
    }

    @Override
    public List<Product> getNewProducts() {
        return productMapper.getNewProducts();
    }

    @Override
    public List<Product> getAdminProductList() {
        return productMapper.getAdminProductList();
    }

    // 发送信息 MQTT
//    public void send(String payload){
//        outboundChannel.send(MessageBuilder.withPayload(payload).build());
//    }
}

