package com.fmcd.market.entity.goods;
import jakarta.persistence.*;

@Entity
@Table(name = "t_goods")
public class Goods {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;



    public Goods() {}
    public Goods(Integer id, String name, Double price) {
        this.id = id; this.name = name; this.price = price;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}