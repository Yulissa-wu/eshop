package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ord_num;

    @Column(name = "cust_num", nullable = false)
    private int cust_num;

    @Column(name = "order_date", nullable = false)
    private LocalDate order_date;

    @Column(name = "county", nullable = false)
    private String county;

    // Constructors
    public Order() {}

    public Order(int cust_num, LocalDate order_date, String county) {
        this.cust_num = cust_num;
        this.order_date = order_date;
        this.county = county;
    }

    // Getters and Setters
    public int getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(int ord_num) {
        this.ord_num = ord_num;
    }

    public int getCust_num() {
        return cust_num;
    }

    public void setCust_num(int cust_num) {
        this.cust_num = cust_num;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ord_num=" + ord_num +
                ", cust_num=" + cust_num +
                ", order_date=" + order_date +
                ", county='" + county + '\'' +
                '}';
    }
}
