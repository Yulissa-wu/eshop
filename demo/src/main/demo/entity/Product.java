package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "產品名稱不能為空")
    @Size(max = 100, message = "產品名稱長度不能超過100字")
    @Column(name = "prod_name")
    private String prodName;

    @Size(max = 255, message = "產品線長度不能超過255字")
    @Column(name = "prod_line")
    private String prodLine;

    @Size(max = 255, message = "產品類型長度不能超過255字")
    @Column(name = "prod_type")
    private String prodType;

    @NotNull(message = "價格不能為空")
    @PositiveOrZero(message = "價格必須大於等於0")
    @Column(name = "pro_price")
    private Double prodPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    // === Getter / Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdLine() {
        return prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
