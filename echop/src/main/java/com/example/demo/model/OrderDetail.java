package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detail_id; // ✅ 自己的主鍵

    @ManyToOne
    @JoinColumn(name = "ord_num", nullable = false)
    private Order order; // ✅ 關聯到 Orders（外鍵）

    @Column(name = "prod_num", nullable = false)
    private int prod_num; // 對應 products 表

    @Column(name = "ord_qty", nullable = false)
    private int ord_qty; // 訂購數量

    @Column(name = "ord_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal ord_price; // 單價或金額

    // Constructors
    public OrderDetail() {}

    public OrderDetail(Order order, int prod_num, int ord_qty, BigDecimal ord_price) {
        this.order = order;
        this.prod_num = prod_num;
        this.ord_qty = ord_qty;
        this.ord_price = ord_price;
    }

    // Getters and Setters
    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getProd_num() {
        return prod_num;
    }

    public void setProd_num(int prod_num) {
        this.prod_num = prod_num;
    }

    public int getOrd_qty() {
        return ord_qty;
    }

    public void setOrd_qty(int ord_qty) {
        this.ord_qty = ord_qty;
    }

    public BigDecimal getOrd_price() {
        return ord_price;
    }

    public void setOrd_price(BigDecimal ord_price) {
        this.ord_price = ord_price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "detail_id=" + detail_id +
                ", prod_num=" + prod_num +
                ", ord_qty=" + ord_qty +
                ", ord_price=" + ord_price +
                '}';
    }
}
