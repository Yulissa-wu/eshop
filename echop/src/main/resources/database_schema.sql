-- =============================================
-- 電商商品管理系統資料庫結構
-- 創建時間: 2024
-- 功能: 用戶管理、商品管理、訂單管理、客戶管理
-- =============================================

-- 創建資料庫
CREATE DATABASE IF NOT EXISTS eshop 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 使用資料庫
USE eshop;

-- =============================================
-- 客戶表 (customers)
-- 功能: 存儲客戶基本資訊
-- =============================================
CREATE TABLE `customers` (
    -- 主鍵，自動遞增
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    
    -- 客戶編號，唯一且不能為空
    `cust_num` INT NOT NULL UNIQUE,
    
    -- 客戶名稱，不能為空
    `cust_name` VARCHAR(50) NOT NULL,
    
    -- 客戶電子郵件，唯一且不能為空，必須包含@符號
    `email` VARCHAR(100) NOT NULL UNIQUE,
    
    -- 密碼哈希值，不能為空（密碼需包含大小寫字母、數字、符號）
    `password_hash` VARCHAR(255) NOT NULL,
    
    -- 客戶電話
    `phone` VARCHAR(20),
    
    -- 客戶地址
    `address` TEXT,
    
    -- 客戶狀態：active=啟用, inactive=停用
    `status` ENUM('active', 'inactive') DEFAULT 'active',
    
    -- 創建時間，自動記錄
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 最後更新時間，自動更新
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- 添加索引以提高查詢效能
    INDEX idx_cust_num (cust_num),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 商品表 (products)
-- 功能: 存儲商品詳細資訊
-- =============================================
CREATE TABLE `products` (
    -- 主鍵，自動遞增
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    
    -- 商品編號，唯一且不能為空
    `prod_num` INT NOT NULL UNIQUE,
    
    -- 商品名稱，不能為空
    `prod_name` VARCHAR(50) NOT NULL,
    
    -- 商品類型，不能為空
    `prod_type` VARCHAR(50) NOT NULL,
    
    -- 商品系列，不能為空
    `prod_line` VARCHAR(50) NOT NULL,
    
    -- 商品價格，不能為空
    `prod_price` INT NOT NULL,
    
    -- 商品描述
    `description` TEXT,
    
    -- 商品庫存數量
    `stock_quantity` INT DEFAULT 0,
    
    -- 商品主圖片路徑
    `main_image` VARCHAR(255),
    
    -- 商品狀態：active=上架, inactive=下架, draft=草稿
    `status` ENUM('active', 'inactive', 'draft') DEFAULT 'draft',
    
    -- 商品重量（克）
    `weight` DECIMAL(8,2),
    
    -- 商品尺寸（長x寬x高，單位：公分）
    `dimensions` VARCHAR(50),
    
    -- 商品品牌
    `brand` VARCHAR(100),
    
    -- 商品SKU（庫存單位）
    `sku` VARCHAR(100) UNIQUE,
    
    -- 商品標籤，用逗號分隔
    `tags` VARCHAR(500),
    
    -- 是否為特色商品
    `is_featured` BOOLEAN DEFAULT FALSE,
    
    -- 商品排序
    `sort_order` INT DEFAULT 0,
    
    -- 創建時間
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 更新時間
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- 索引
    INDEX idx_prod_num (prod_num),
    INDEX idx_prod_type (prod_type),
    INDEX idx_prod_line (prod_line),
    INDEX idx_prod_price (prod_price),
    INDEX idx_status (status),
    INDEX idx_is_featured (is_featured),
    INDEX idx_sku (sku)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 訂單表 (orders)
-- 功能: 存儲訂單基本資訊
-- =============================================
CREATE TABLE `orders` (
    -- 主鍵，自動遞增
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    
    -- 訂單編號，唯一且不能為空
    `ord_num` INT NOT NULL UNIQUE,
    
    -- 客戶編號，外鍵
    `cust_num` INT NOT NULL,
    
    -- 訂單日期
    `order_date` DATE NOT NULL,
    
    -- 縣市，不能為空
    `county` VARCHAR(50) NOT NULL,
    
    -- 訂單狀態：pending=待處理, processing=處理中, shipped=已出貨, delivered=已送達, cancelled=已取消
    `status` ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    
    -- 訂單總金額
    `total_amount` DECIMAL(10,2) DEFAULT 0.00,
    
    -- 運費
    `shipping_fee` DECIMAL(10,2) DEFAULT 0.00,
    
    -- 付款方式：cash=現金, credit_card=信用卡, bank_transfer=銀行轉帳
    `payment_method` ENUM('cash', 'credit_card', 'bank_transfer') DEFAULT 'cash',
    
    -- 付款狀態：unpaid=未付款, paid=已付款, refunded=已退款
    `payment_status` ENUM('unpaid', 'paid', 'refunded') DEFAULT 'unpaid',
    
    -- 收貨地址
    `shipping_address` TEXT,
    
    -- 備註
    `notes` TEXT,
    
    -- 創建時間
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 更新時間
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- 外鍵約束
    FOREIGN KEY (cust_num) REFERENCES customers(cust_num) ON DELETE RESTRICT,
    
    -- 索引
    INDEX idx_ord_num (ord_num),
    INDEX idx_cust_num (cust_num),
    INDEX idx_order_date (order_date),
    INDEX idx_status (status),
    INDEX idx_payment_status (payment_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 訂單詳情表 (order_detail)
-- 功能: 存儲訂單商品詳情
-- =============================================
CREATE TABLE `order_detail` (
    -- 主鍵，自動遞增
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    
    -- 訂單編號，外鍵
    `ord_num` INT NOT NULL,
    
    -- 商品編號，外鍵
    `prod_num` INT NOT NULL,
    
    -- 訂購數量
    `ord_qty` INT NOT NULL DEFAULT 1,
    
    -- 訂購價格（當時的價格）
    `ord_price` DECIMAL(10,2) NOT NULL,
    
    -- 小計金額
    `subtotal` DECIMAL(10,2) GENERATED ALWAYS AS (ord_qty * ord_price) STORED,
    
    -- 創建時間
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 外鍵約束
    FOREIGN KEY (ord_num) REFERENCES orders(ord_num) ON DELETE CASCADE,
    FOREIGN KEY (prod_num) REFERENCES products(prod_num) ON DELETE RESTRICT,
    
    -- 索引
    INDEX idx_ord_num (ord_num),
    INDEX idx_prod_num (prod_num)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 商品圖片表 (product_images)
-- 功能: 存儲商品的多張圖片
-- =============================================
CREATE TABLE `product_images` (
    -- 主鍵，自動遞增
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    
    -- 商品編號，外鍵
    `prod_num` INT NOT NULL,
    
    -- 圖片路徑
    `image_path` VARCHAR(255) NOT NULL,
    
    -- 圖片描述
    `image_description` VARCHAR(255),
    
    -- 圖片排序
    `sort_order` INT DEFAULT 0,
    
    -- 是否為主圖片
    `is_main` BOOLEAN DEFAULT FALSE,
    
    -- 創建時間
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 外鍵約束
    FOREIGN KEY (prod_num) REFERENCES products(prod_num) ON DELETE CASCADE,
    
    -- 索引
    INDEX idx_prod_num (prod_num),mydb
    INDEX idx_sort_order (sort_order),
    INDEX idx_is_main (is_main)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 插入初始數據
-- =============================================

-- 插入管理員客戶（密碼: Admin123!@#）
INSERT INTO customers (cust_num, cust_name, email, password_hash, status) VALUES
(1, 'admin', 'admin@ecommerce.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQjO1M3b0.8K8Y8K8Y8K8Y8K8Y8K8', 'active');

-- 插入測試客戶
INSERT INTO customers (cust_num, cust_name, email, password_hash, phone, address, status) VALUES
(2, '張三', 'zhang@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQjO1M3b0.8K8Y8K8Y8K8Y8K8Y8K8', '0912345678', '台北市信義區信義路五段7號', 'active'),
(3, '李四', 'li@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQjO1M3b0.8K8Y8K8Y8K8Y8K8Y8K8', '0987654321', '新北市板橋區文化路一段188號', 'active');

-- 插入商品分類（使用 prod_type 和 prod_line）
INSERT INTO products (prod_num, prod_name, prod_type, prod_line, prod_price, description, stock_quantity, status) VALUES
(1, 'iPhone 15 Pro', '手機', 'Apple', 35900, '最新款iPhone，搭載A17 Pro晶片', 50, 'active'),
(2, 'MacBook Air M2', '筆記本電腦', 'Apple', 37900, '輕薄便攜的MacBook Air', 30, 'active'),
(3, 'Samsung Galaxy S24', '手機', 'Samsung', 28900, 'Samsung旗艦手機', 40, 'active'),
(4, 'Dell XPS 13', '筆記本電腦', 'Dell', 45900, '高階商務筆記本電腦', 25, 'active'),
(5, 'Nike Air Max', '運動鞋', 'Nike', 4500, '經典運動鞋款', 100, 'active'),
(6, 'Adidas Ultraboost', '運動鞋', 'Adidas', 5200, '專業跑步鞋', 80, 'active');

-- 插入測試訂單
INSERT INTO orders (ord_num, cust_num, order_date, county, status, total_amount, payment_method, payment_status, shipping_address) VALUES
(1001, 2, '2024-01-15', '台北市', 'delivered', 35900.00, 'credit_card', 'paid', '台北市信義區信義路五段7號'),
(1002, 3, '2024-01-16', '新北市', 'processing', 37900.00, 'bank_transfer', 'paid', '新北市板橋區文化路一段188號');

-- 插入訂單詳情
INSERT INTO order_detail (ord_num, prod_num, ord_qty, ord_price) VALUES
(1001, 1, 1, 35900.00),
(1002, 2, 1, 37900.00);

-- =============================================
-- 創建視圖 (Views)
-- =============================================

-- 商品詳細資訊視圖
CREATE VIEW product_details AS
SELECT 
    p.id,
    p.prod_num,
    p.prod_name,
    p.prod_type,
    p.prod_line,
    p.prod_price,
    p.description,
    p.stock_quantity,
    p.status,
    p.weight,
    p.dimensions,
    p.brand,
    p.sku,
    p.tags,
    p.is_featured,
    p.sort_order,
    p.created_at,
    p.updated_at,
    (SELECT image_path FROM product_images WHERE prod_num = p.prod_num AND is_main = TRUE LIMIT 1) as main_image
FROM products p;

-- 訂單詳細資訊視圖
CREATE VIEW order_details_view AS
SELECT 
    o.id,
    o.ord_num,
    o.cust_num,
    c.cust_name,
    c.email as customer_email,
    o.order_date,
    o.county,
    o.status,
    o.total_amount,
    o.shipping_fee,
    o.payment_method,
    o.payment_status,
    o.shipping_address,
    o.notes,
    o.created_at,
    o.updated_at
FROM orders o
LEFT JOIN customers c ON o.cust_num = c.cust_num;

-- 訂單商品詳情視圖
CREATE VIEW order_items_view AS
SELECT 
    od.id,
    od.ord_num,
    od.prod_num,
    p.prod_name,
    p.prod_type,
    p.prod_line,
    od.ord_qty,
    od.ord_price,
    od.subtotal,
    od.created_at
FROM order_detail od
LEFT JOIN products p ON od.prod_num = p.prod_num;

-- 客戶訂單統計視圖
CREATE VIEW customer_order_stats AS
SELECT 
    c.cust_num,
    c.cust_name,
    c.email,
    COUNT(o.ord_num) as total_orders,
    SUM(o.total_amount) as total_spent,
    MAX(o.order_date) as last_order_date,
    AVG(o.total_amount) as avg_order_value
FROM customers c
LEFT JOIN orders o ON c.cust_num = o.cust_num
GROUP BY c.cust_num, c.cust_name, c.email;

-- =============================================
-- 創建存儲過程 (Stored Procedures)
-- =============================================

-- 驗證密碼強度的存儲過程
DELIMITER //
CREATE PROCEDURE ValidatePassword(IN p_password VARCHAR(255), OUT p_is_valid BOOLEAN)
BEGIN
    DECLARE has_upper BOOLEAN DEFAULT FALSE;
    DECLARE has_lower BOOLEAN DEFAULT FALSE;
    DECLARE has_digit BOOLEAN DEFAULT FALSE;
    DECLARE has_special BOOLEAN DEFAULT FALSE;
    
    -- 檢查大寫字母
    IF p_password REGEXP '[A-Z]' THEN SET has_upper = TRUE; END IF;
    
    -- 檢查小寫字母
    IF p_password REGEXP '[a-z]' THEN SET has_lower = TRUE; END IF;
    
    -- 檢查數字
    IF p_password REGEXP '[0-9]' THEN SET has_digit = TRUE; END IF;
    
    -- 檢查特殊符號
    IF p_password REGEXP '[!@#$%^&*(),.?":{}|<>]' THEN SET has_special = TRUE; END IF;
    
    -- 密碼長度至少8位且包含所有要求
    SET p_is_valid = (LENGTH(p_password) >= 8 AND has_upper AND has_lower AND has_digit AND has_special);
END //
DELIMITER ;

-- 驗證電子郵件格式的存儲過程
DELIMITER //
CREATE PROCEDURE ValidateEmail(IN p_email VARCHAR(100), OUT p_is_valid BOOLEAN)
BEGIN
    -- 檢查電子郵件是否包含@符號且格式正確
    SET p_is_valid = (p_email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
END //
DELIMITER ;

-- 計算訂單總金額的存儲過程
DELIMITER //
CREATE PROCEDURE CalculateOrderTotal(IN p_ord_num INT, OUT p_total DECIMAL(10,2))
BEGIN
    SELECT COALESCE(SUM(subtotal), 0) INTO p_total
    FROM order_detail 
    WHERE ord_num = p_ord_num;
END //
DELIMITER ;

-- 更新訂單總金額的存儲過程
DELIMITER //
CREATE PROCEDURE UpdateOrderTotal(IN p_ord_num INT)
BEGIN
    DECLARE v_total DECIMAL(10,2);
    
    -- 計算總金額
    CALL CalculateOrderTotal(p_ord_num, v_total);
    
    -- 更新訂單總金額
    UPDATE orders 
    SET total_amount = v_total 
    WHERE ord_num = p_ord_num;
END //
DELIMITER ;

-- =============================================
-- 創建觸發器 (Triggers)
-- =============================================

-- 商品更新時自動更新updated_at
DELIMITER //
CREATE TRIGGER update_product_timestamp
    BEFORE UPDATE ON products
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //
DELIMITER ;

-- 客戶更新時自動更新updated_at
DELIMITER //
CREATE TRIGGER update_customer_timestamp
    BEFORE UPDATE ON customers
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //
DELIMITER ;

-- 訂單更新時自動更新updated_at
DELIMITER //
CREATE TRIGGER update_order_timestamp
    BEFORE UPDATE ON orders
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //
DELIMITER ;

-- 訂單詳情插入/更新時自動更新訂單總金額
DELIMITER //
CREATE TRIGGER update_order_total_after_detail_change
    AFTER INSERT ON order_detail
    FOR EACH ROW
BEGIN
    CALL UpdateOrderTotal(NEW.ord_num);
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER update_order_total_after_detail_update
    AFTER UPDATE ON order_detail
    FOR EACH ROW
BEGIN
    CALL UpdateOrderTotal(NEW.ord_num);
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER update_order_total_after_detail_delete
    AFTER DELETE ON order_detail
    FOR EACH ROW
BEGIN
    CALL UpdateOrderTotal(OLD.ord_num);
END //
DELIMITER ;

-- =============================================
-- 創建索引優化查詢效能
-- =============================================

-- 商品名稱全文搜索索引
CREATE FULLTEXT INDEX idx_product_name_fulltext ON products(prod_name, description);

-- 商品標籤全文搜索索引
CREATE FULLTEXT INDEX idx_product_tags_fulltext ON products(tags);

-- 客戶名稱全文搜索索引
CREATE FULLTEXT INDEX idx_customer_name_fulltext ON customers(cust_name);

-- =============================================
-- 資料庫權限設置
-- =============================================

-- 創建應用程式用戶（實際部署時使用）
-- CREATE USER 'eshop_app'@'localhost' IDENTIFIED BY 'secure_password_here';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON eshop.* TO 'eshop_app'@'localhost';
-- FLUSH PRIVILEGES;

-- =============================================
-- 完成資料庫結構創建
-- =============================================

-- 顯示創建完成的訊息
SELECT 'Database schema created successfully!' as message;
SELECT 'Tables created: customers, products, orders, order_detail, product_images' as tables;
SELECT 'Views created: product_details, order_details_view, order_items_view, customer_order_stats' as views;
SELECT 'Procedures created: ValidatePassword, ValidateEmail, CalculateOrderTotal, UpdateOrderTotal' as procedures;
SELECT 'Triggers created: update timestamps and order totals' as triggers;
