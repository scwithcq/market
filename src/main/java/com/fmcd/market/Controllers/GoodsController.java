package com.fmcd.market.Controllers;

import com.fmcd.market.Entity.goods.Goods;
import com.fmcd.market.Entity.goods.GoodsRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//这个Goods是我用来测试的
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GoodsController {

    private final GoodsRepository repo;

    public GoodsController(GoodsRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/goods")
    public List<Goods> list() {
        return repo.findAll();   // ← 这里就是从数据库拿
    }
}