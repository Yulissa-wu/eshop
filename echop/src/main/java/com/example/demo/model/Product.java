package com.example.demo.model;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "prod_num", nullable = false)
    private int prod_num;

    @Column(name = "prod_name", nullable = false)
    private String prod_name;

    @Column(name = "prod_type", nullable = false)
    private String prod_type;

    @Column(name = "prod_line", nullable = false)
    private String prod_line;

    @Column(name = "img_url", nullable = false)
    private String img_url;
    
    @Column(name = "pro_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal pro_price;

    // Constructors
    public Product() {}

//    public Product(String prod_name, String prod_type, String prod_line, BigDecimal pro_price) {
//        this.prod_name = prod_name;
//        this.prod_type = prod_type;
//        this.prod_line = prod_line;
//        this.pro_price = pro_price;
//    }

    // Getters and Setters
    public Long getId() {
		return id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public int getProd_num() {
        return prod_num;
    }

    public void setProd_num(int prod_num) {
        this.prod_num = prod_num;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_type() {
        return prod_type;
    }

    public void setProd_type(String prod_type) {
        this.prod_type = prod_type;
    }

    public String getProd_line() {
        return prod_line;
    }

    public void setProd_line(String prod_line) {
        this.prod_line = prod_line;
    }

    public BigDecimal getPro_price() {
        return pro_price;
    }

    public void setPro_price(BigDecimal pro_price) {
        this.pro_price = pro_price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "prod_num=" + prod_num +
                ", prod_name='" + prod_name + '\'' +
                ", prod_type='" + prod_type + '\'' +
                ", prod_line='" + prod_line + '\'' +
                ", pro_price=" + pro_price +
                '}';
    }
}
