// =============================================
// 電商商品管理系統 - 後端服務類
// 創建時間: 2024
// 功能: 客戶管理、商品管理、訂單管理、圖片上傳
// =============================================

package com.example.demo.service;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;
import java.nio.file.*;
import org.springframework.stereotype.Service;

/**
 * 電商商品管理系統主要服務類
 * 提供客戶註冊登錄、商品管理、訂單管理、圖片上傳等功能
 */
@Service
public class EcommerceService {
    
    // =============================================
    // 類別常數和變數
    // =============================================
    
    // 資料庫連接相關常數
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eshop?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "wsq"; // 實際部署時使用專用用戶
    private static final String DB_PASSWORD = "004328qwer"; // 實際部署時使用環境變數
    
    // 圖片上傳相關常數
    private static final String UPLOAD_DIR = "uploads/"; // 圖片上傳目錄
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 最大檔案大小 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
    
    // 密碼強度驗證正則表達式
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );
    
    // 電子郵件格式驗證正則表達式
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // 日誌記錄器
    private static final Logger logger = Logger.getLogger(EcommerceService.class.getName());
    
    // 資料庫連接
    private Connection connection;
    
    // =============================================
    // 建構函數
    // =============================================
    
    /**
     * 建構函數 - 初始化服務
     */
    public EcommerceService() {
        // 記錄服務初始化日誌
        logger.info("EcommerceService 初始化");
        
        // 創建上傳目錄（如果不存在）
        createUploadDirectory();
    }
    
    /**
     * 獲取資料庫連接
     * @return 資料庫連接
     * @throws SQLException 資料庫連接異常
     */
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // 載入 MySQL JDBC 驅動程式
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // 建立資料庫連接
                this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                
                // 記錄成功連接日誌
                logger.info("資料庫連接成功建立");
                
            } catch (ClassNotFoundException e) {
                // 記錄驅動程式載入失敗日誌
                logger.severe("MySQL JDBC 驅動程式載入失敗: " + e.getMessage());
                throw new RuntimeException("資料庫驅動程式載入失敗", e);
            }
        }
        return connection;
    }
    
    // =============================================
    // 客戶管理功能
    // =============================================
    
    /**
     * 客戶註冊功能
     * @param custName 客戶名稱
     * @param email 電子郵件
     * @param password 密碼
     * @param phone 電話
     * @param address 地址
     * @return 註冊結果
     */
    public Map<String, Object> registerCustomer(String custName, String email, String password, String phone, String address) {
        // 記錄註冊開始日誌
        logger.info("開始處理客戶註冊請求 - 客戶名稱: " + custName + ", 電子郵件: " + email);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 驗證輸入參數
            if (!validateRegistrationInput(custName, email, password)) {
                result.put("success", false);
                result.put("message", "輸入資料格式不正確");
                logger.warning("註冊失敗 - 輸入資料格式不正確");
                return result;
            }
            
            // 檢查客戶名稱是否已存在
            if (isCustomerNameExists(custName)) {
                result.put("success", false);
                result.put("message", "客戶名稱已存在");
                logger.warning("註冊失敗 - 客戶名稱已存在: " + custName);
                return result;
            }
            
            // 檢查電子郵件是否已存在
            if (isEmailExists(email)) {
                result.put("success", false);
                result.put("message", "電子郵件已存在");
                logger.warning("註冊失敗 - 電子郵件已存在: " + email);
                return result;
            }
            
            // 加密密碼
            String hashedPassword = hashPassword(password);
            
            // 生成客戶編號
            int custNum = generateCustomerNumber();
            
            // 插入新客戶到資料庫
            String insertSQL = "INSERT INTO customers (cust_num, cust_name, email, password_hash, phone, address, status) VALUES (?, ?, ?, ?, ?, ?, 'active')";
            try (PreparedStatement pstmt = getConnection().prepareStatement(insertSQL)) {
                pstmt.setInt(1, custNum);
                pstmt.setString(2, custName);
                pstmt.setString(3, email);
                pstmt.setString(4, hashedPassword);
                pstmt.setString(5, phone);
                pstmt.setString(6, address);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    result.put("success", true);
                    result.put("message", "註冊成功");
                    result.put("cust_num", custNum);
                    logger.info("客戶註冊成功 - 客戶名稱: " + custName + ", 客戶編號: " + custNum);
                } else {
                    result.put("success", false);
                    result.put("message", "註冊失敗");
                    logger.severe("註冊失敗 - 資料庫插入失敗");
                }
            }
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("註冊過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    /**
     * 客戶登錄功能
     * @param loginId 客戶名稱或電子郵件
     * @param password 密碼
     * @return 登錄結果
     */
    public Map<String, Object> loginCustomer(String loginId, String password) {
        // 記錄登錄開始日誌
        logger.info("開始處理客戶登錄請求 - 登錄ID: " + loginId);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 查詢客戶資訊
            String selectSQL = "SELECT id, cust_num, cust_name, email, password_hash, status FROM customers WHERE (cust_name = ? OR email = ?) AND status = 'active'";
            try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
                pstmt.setString(1, loginId);
                pstmt.setString(2, loginId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // 驗證密碼
                        String storedPassword = rs.getString("password_hash");
                        if (verifyPassword(password, storedPassword)) {
                            // 登錄成功
                            result.put("success", true);
                            result.put("message", "登錄成功");
                            result.put("id", rs.getInt("id"));
                            result.put("cust_num", rs.getInt("cust_num"));
                            result.put("cust_name", rs.getString("cust_name"));
                            result.put("email", rs.getString("email"));
                            
                            logger.info("客戶登錄成功 - 客戶名稱: " + rs.getString("cust_name") + ", 客戶編號: " + rs.getInt("cust_num"));
                        } else {
                            // 密碼錯誤
                            result.put("success", false);
                            result.put("message", "密碼錯誤");
                            logger.warning("登錄失敗 - 密碼錯誤: " + loginId);
                        }
                    } else {
                        // 客戶不存在
                        result.put("success", false);
                        result.put("message", "客戶不存在或帳戶已停用");
                        logger.warning("登錄失敗 - 客戶不存在: " + loginId);
                    }
                }
            }
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("登錄過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    // =============================================
    // 商品管理功能
    // =============================================
    
    /**
     * 新增商品
     * @param productData 商品資料
     * @return 新增結果
     */
    public Map<String, Object> addProduct(Map<String, Object> productData) {
        // 記錄新增商品開始日誌
        logger.info("開始處理新增商品請求 - 商品名稱: " + productData.get("prod_name"));
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 驗證商品資料
            if (!validateProductData(productData)) {
                result.put("success", false);
                result.put("message", "商品資料格式不正確");
                logger.warning("新增商品失敗 - 資料格式不正確");
                return result;
            }
            
            // 生成商品編號
            int prodNum = generateProductNumber();
            
            // 插入商品到資料庫
            String insertSQL = "INSERT INTO products (prod_num, prod_name, prod_type, prod_line, prod_price, description, stock_quantity, main_image, status, weight, dimensions, brand, sku, tags, is_featured, sort_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, prodNum);
                pstmt.setString(2, (String) productData.get("prod_name"));
                pstmt.setString(3, (String) productData.get("prod_type"));
                pstmt.setString(4, (String) productData.get("prod_line"));
                pstmt.setInt(5, Integer.parseInt(productData.get("prod_price").toString()));
                pstmt.setString(6, (String) productData.get("description"));
                pstmt.setInt(7, Integer.parseInt(productData.getOrDefault("stock_quantity", "0").toString()));
                pstmt.setString(8, (String) productData.get("main_image"));
                pstmt.setString(9, (String) productData.getOrDefault("status", "draft"));
                pstmt.setBigDecimal(10, productData.get("weight") != null ? new java.math.BigDecimal(productData.get("weight").toString()) : null);
                pstmt.setString(11, (String) productData.get("dimensions"));
                pstmt.setString(12, (String) productData.get("brand"));
                pstmt.setString(13, (String) productData.get("sku"));
                pstmt.setString(14, (String) productData.get("tags"));
                pstmt.setBoolean(15, Boolean.parseBoolean(productData.getOrDefault("is_featured", "false").toString()));
                pstmt.setInt(16, Integer.parseInt(productData.getOrDefault("sort_order", "0").toString()));
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // 獲取新插入的商品ID
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int productId = generatedKeys.getInt(1);
                            result.put("success", true);
                            result.put("message", "商品新增成功");
                            result.put("id", productId);
                            result.put("prod_num", prodNum);
                            logger.info("商品新增成功 - 商品ID: " + productId + ", 商品編號: " + prodNum + ", 商品名稱: " + productData.get("prod_name"));
                        }
                    }
                } else {
                    result.put("success", false);
                    result.put("message", "商品新增失敗");
                    logger.severe("商品新增失敗 - 資料庫插入失敗");
                }
            }
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("新增商品過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        } catch (Exception e) {
            // 記錄其他錯誤日誌
            logger.severe("新增商品過程中發生錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    /**
     * 刪除商品
     * @param prodNum 商品編號
     * @return 刪除結果
     */
    public Map<String, Object> deleteProduct(int prodNum) {
        // 記錄刪除商品開始日誌
        logger.info("開始處理刪除商品請求 - 商品編號: " + prodNum);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 檢查商品是否存在
            if (!isProductExists(prodNum)) {
                result.put("success", false);
                result.put("message", "商品不存在");
                logger.warning("刪除商品失敗 - 商品不存在: " + prodNum);
                return result;
            }
            
            // 檢查是否有相關訂單
            if (hasRelatedOrders(prodNum)) {
                result.put("success", false);
                result.put("message", "無法刪除，該商品已有相關訂單");
                logger.warning("刪除商品失敗 - 商品已有相關訂單: " + prodNum);
                return result;
            }
            
            // 刪除商品相關圖片檔案
            deleteProductImages(prodNum);
            
            // 從資料庫刪除商品
            String deleteSQL = "DELETE FROM products WHERE prod_num = ?";
            try (PreparedStatement pstmt = getConnection().prepareStatement(deleteSQL)) {
                pstmt.setInt(1, prodNum);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    result.put("success", true);
                    result.put("message", "商品刪除成功");
                    logger.info("商品刪除成功 - 商品編號: " + prodNum);
                } else {
                    result.put("success", false);
                    result.put("message", "商品刪除失敗");
                    logger.severe("商品刪除失敗 - 資料庫操作失敗");
                }
            }
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("刪除商品過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    /**
     * 獲取商品列表
     * @param prodType 商品類型（可選）
     * @param prodLine 商品系列（可選）
     * @param status 商品狀態（可選）
     * @param page 頁碼
     * @param pageSize 每頁數量
     * @return 商品列表
     */
    public Map<String, Object> getProducts(String prodType, String prodLine, String status, int page, int pageSize) {
        // 記錄獲取商品列表開始日誌
        logger.info("開始處理獲取商品列表請求 - 類型: " + prodType + ", 系列: " + prodLine + ", 狀態: " + status + ", 頁碼: " + page);
        
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        
        try {
            // 構建查詢SQL
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT * FROM products WHERE 1=1 ");
            
            List<Object> parameters = new ArrayList<>();
            
            // 添加類型篩選條件
            if (prodType != null && !prodType.isEmpty()) {
                sqlBuilder.append("AND prod_type = ? ");
                parameters.add(prodType);
            }
            
            // 添加系列篩選條件
            if (prodLine != null && !prodLine.isEmpty()) {
                sqlBuilder.append("AND prod_line = ? ");
                parameters.add(prodLine);
            }
            
            // 添加狀態篩選條件
            if (status != null && !status.isEmpty()) {
                sqlBuilder.append("AND status = ? ");
                parameters.add(status);
            }
            
            sqlBuilder.append("ORDER BY sort_order ASC, created_at DESC ");
            sqlBuilder.append("LIMIT ? OFFSET ? ");
            
            int offset = (page - 1) * pageSize;
            parameters.add(pageSize);
            parameters.add(offset);
            
            // 執行查詢
            try (PreparedStatement pstmt = getConnection().prepareStatement(sqlBuilder.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    pstmt.setObject(i + 1, parameters.get(i));
                }
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> product = new HashMap<>();
                        product.put("id", rs.getInt("id"));
                        product.put("prod_num", rs.getInt("prod_num"));
                        product.put("prod_name", rs.getString("prod_name"));
                        product.put("prod_type", rs.getString("prod_type"));
                        product.put("prod_line", rs.getString("prod_line"));
                        product.put("prod_price", rs.getInt("prod_price"));
                        product.put("description", rs.getString("description"));
                        product.put("stock_quantity", rs.getInt("stock_quantity"));
                        product.put("main_image", rs.getString("main_image"));
                        product.put("status", rs.getString("status"));
                        product.put("weight", rs.getBigDecimal("weight"));
                        product.put("dimensions", rs.getString("dimensions"));
                        product.put("brand", rs.getString("brand"));
                        product.put("sku", rs.getString("sku"));
                        product.put("tags", rs.getString("tags"));
                        product.put("is_featured", rs.getBoolean("is_featured"));
                        product.put("sort_order", rs.getInt("sort_order"));
                        product.put("created_at", rs.getTimestamp("created_at"));
                        product.put("updated_at", rs.getTimestamp("updated_at"));
                        
                        products.add(product);
                    }
                }
            }
            
            // 獲取總數量
            int totalCount = getProductCount(prodType, prodLine, status);
            
            result.put("success", true);
            result.put("products", products);
            result.put("total_count", totalCount);
            result.put("page", page);
            result.put("page_size", pageSize);
            result.put("total_pages", (int) Math.ceil((double) totalCount / pageSize));
            
            logger.info("商品列表獲取成功 - 返回 " + products.size() + " 個商品");
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("獲取商品列表過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    // =============================================
    // 訂單管理功能
    // =============================================
    
    /**
     * 創建訂單
     * @param orderData 訂單資料
     * @return 創建結果
     */
    public Map<String, Object> createOrder(Map<String, Object> orderData) {
        // 記錄創建訂單開始日誌
        logger.info("開始處理創建訂單請求 - 客戶編號: " + orderData.get("cust_num"));
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 驗證訂單資料
            if (!validateOrderData(orderData)) {
                result.put("success", false);
                result.put("message", "訂單資料格式不正確");
                logger.warning("創建訂單失敗 - 資料格式不正確");
                return result;
            }
            
            // 生成訂單編號
            int ordNum = generateOrderNumber();
            
            // 開始事務
            getConnection().setAutoCommit(false);
            
            try {
                // 插入訂單
                String insertOrderSQL = "INSERT INTO orders (ord_num, cust_num, order_date, county, status, total_amount, payment_method, payment_status, shipping_address, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = getConnection().prepareStatement(insertOrderSQL)) {
                    pstmt.setInt(1, ordNum);
                    pstmt.setInt(2, Integer.parseInt(orderData.get("cust_num").toString()));
                    pstmt.setDate(3, java.sql.Date.valueOf(orderData.get("order_date").toString()));
                    pstmt.setString(4, (String) orderData.get("county"));
                    pstmt.setString(5, (String) orderData.getOrDefault("status", "pending"));
                    pstmt.setBigDecimal(6, new java.math.BigDecimal(orderData.getOrDefault("total_amount", "0").toString()));
                    pstmt.setString(7, (String) orderData.getOrDefault("payment_method", "cash"));
                    pstmt.setString(8, (String) orderData.getOrDefault("payment_status", "unpaid"));
                    pstmt.setString(9, (String) orderData.get("shipping_address"));
                    pstmt.setString(10, (String) orderData.get("notes"));
                    
                    pstmt.executeUpdate();
                }
                
                // 插入訂單詳情
                List<Map<String, Object>> orderItems = (List<Map<String, Object>>) orderData.get("order_items");
                if (orderItems != null && !orderItems.isEmpty()) {
                    String insertDetailSQL = "INSERT INTO order_detail (ord_num, prod_num, ord_qty, ord_price) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = getConnection().prepareStatement(insertDetailSQL)) {
                        for (Map<String, Object> item : orderItems) {
                            pstmt.setInt(1, ordNum);
                            pstmt.setInt(2, Integer.parseInt(item.get("prod_num").toString()));
                            pstmt.setInt(3, Integer.parseInt(item.get("ord_qty").toString()));
                            pstmt.setBigDecimal(4, new java.math.BigDecimal(item.get("ord_price").toString()));
                            pstmt.addBatch();
                        }
                        pstmt.executeBatch();
                    }
                }
                
                // 提交事務
                getConnection().commit();
                
                result.put("success", true);
                result.put("message", "訂單創建成功");
                result.put("ord_num", ordNum);
                logger.info("訂單創建成功 - 訂單編號: " + ordNum + ", 客戶編號: " + orderData.get("cust_num"));
                
            } catch (SQLException e) {
                // 回滾事務
                getConnection().rollback();
                throw e;
            } finally {
                // 恢復自動提交
                getConnection().setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("創建訂單過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        } catch (Exception e) {
            // 記錄其他錯誤日誌
            logger.severe("創建訂單過程中發生錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    /**
     * 獲取訂單列表
     * @param custNum 客戶編號（可選）
     * @param status 訂單狀態（可選）
     * @param page 頁碼
     * @param pageSize 每頁數量
     * @return 訂單列表
     */
    public Map<String, Object> getOrders(Integer custNum, String status, int page, int pageSize) {
        // 記錄獲取訂單列表開始日誌
        logger.info("開始處理獲取訂單列表請求 - 客戶編號: " + custNum + ", 狀態: " + status + ", 頁碼: " + page);
        
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> orders = new ArrayList<>();
        
        try {
            // 構建查詢SQL
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT o.*, c.cust_name, c.email as customer_email FROM orders o ");
            sqlBuilder.append("LEFT JOIN customers c ON o.cust_num = c.cust_num ");
            sqlBuilder.append("WHERE 1=1 ");
            
            List<Object> parameters = new ArrayList<>();
            
            // 添加客戶篩選條件
            if (custNum != null) {
                sqlBuilder.append("AND o.cust_num = ? ");
                parameters.add(custNum);
            }
            
            // 添加狀態篩選條件
            if (status != null && !status.isEmpty()) {
                sqlBuilder.append("AND o.status = ? ");
                parameters.add(status);
            }
            
            sqlBuilder.append("ORDER BY o.order_date DESC, o.created_at DESC ");
            sqlBuilder.append("LIMIT ? OFFSET ? ");
            
            int offset = (page - 1) * pageSize;
            parameters.add(pageSize);
            parameters.add(offset);
            
            // 執行查詢
            try (PreparedStatement pstmt = getConnection().prepareStatement(sqlBuilder.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    pstmt.setObject(i + 1, parameters.get(i));
                }
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> order = new HashMap<>();
                        order.put("id", rs.getInt("id"));
                        order.put("ord_num", rs.getInt("ord_num"));
                        order.put("cust_num", rs.getInt("cust_num"));
                        order.put("cust_name", rs.getString("cust_name"));
                        order.put("customer_email", rs.getString("customer_email"));
                        order.put("order_date", rs.getDate("order_date"));
                        order.put("county", rs.getString("county"));
                        order.put("status", rs.getString("status"));
                        order.put("total_amount", rs.getBigDecimal("total_amount"));
                        order.put("shipping_fee", rs.getBigDecimal("shipping_fee"));
                        order.put("payment_method", rs.getString("payment_method"));
                        order.put("payment_status", rs.getString("payment_status"));
                        order.put("shipping_address", rs.getString("shipping_address"));
                        order.put("notes", rs.getString("notes"));
                        order.put("created_at", rs.getTimestamp("created_at"));
                        order.put("updated_at", rs.getTimestamp("updated_at"));
                        
                        orders.add(order);
                    }
                }
            }
            
            // 獲取總數量
            int totalCount = getOrderCount(custNum, status);
            
            result.put("success", true);
            result.put("orders", orders);
            result.put("total_count", totalCount);
            result.put("page", page);
            result.put("page_size", pageSize);
            result.put("total_pages", (int) Math.ceil((double) totalCount / pageSize));
            
            logger.info("訂單列表獲取成功 - 返回 " + orders.size() + " 個訂單");
            
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("獲取訂單列表過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    // =============================================
    // 圖片上傳功能
    // =============================================
    
    /**
     * 上傳商品圖片
     * @param prodNum 商品編號
     * @param imageData 圖片資料
     * @param fileName 檔案名稱
     * @param isMain 是否為主圖片
     * @return 上傳結果
     */
    public Map<String, Object> uploadProductImage(int prodNum, byte[] imageData, String fileName, boolean isMain) {
        // 記錄上傳圖片開始日誌
        logger.info("開始處理圖片上傳請求 - 商品編號: " + prodNum + ", 檔案名: " + fileName);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 驗證檔案
            if (!validateImageFile(fileName, imageData.length)) {
                result.put("success", false);
                result.put("message", "檔案格式不正確或檔案過大");
                logger.warning("圖片上傳失敗 - 檔案驗證失敗: " + fileName);
                return result;
            }
            
            // 生成唯一檔案名
            String uniqueFileName = generateUniqueFileName(fileName);
            String filePath = UPLOAD_DIR + uniqueFileName;
            
            // 儲存檔案到磁碟
            Path targetPath = Paths.get(filePath);
            Files.write(targetPath, imageData);
            
            // 如果設為主圖片，先取消其他主圖片
            if (isMain) {
                updateMainImageStatus(prodNum, false);
            }
            
            // 儲存圖片資訊到資料庫
            String insertSQL = "INSERT INTO product_images (prod_num, image_path, image_description, sort_order, is_main) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = getConnection().prepareStatement(insertSQL)) {
                pstmt.setInt(1, prodNum);
                pstmt.setString(2, filePath);
                pstmt.setString(3, "商品圖片");
                pstmt.setInt(4, 0);
                pstmt.setBoolean(5, isMain);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    result.put("success", true);
                    result.put("message", "圖片上傳成功");
                    result.put("file_path", filePath);
                    logger.info("圖片上傳成功 - 商品編號: " + prodNum + ", 檔案路徑: " + filePath);
                } else {
                    result.put("success", false);
                    result.put("message", "圖片資訊儲存失敗");
                    logger.severe("圖片上傳失敗 - 資料庫插入失敗");
                }
            }
            
        } catch (IOException e) {
            // 記錄檔案操作錯誤日誌
            logger.severe("圖片上傳過程中發生檔案操作錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "檔案儲存失敗");
        } catch (SQLException e) {
            // 記錄資料庫錯誤日誌
            logger.severe("圖片上傳過程中發生資料庫錯誤: " + e.getMessage());
            result.put("success", false);
            result.put("message", "系統錯誤，請稍後再試");
        }
        
        return result;
    }
    
    // =============================================
    // 輔助方法
    // =============================================
    
    /**
     * 驗證註冊輸入資料
     * @param custName 客戶名稱
     * @param email 電子郵件
     * @param password 密碼
     * @return 驗證結果
     */
    private boolean validateRegistrationInput(String custName, String email, String password) {
        // 驗證客戶名稱
        if (custName == null || custName.trim().isEmpty() || custName.length() < 2) {
            logger.warning("客戶名稱驗證失敗 - 長度不足或為空");
            return false;
        }
        
        // 驗證電子郵件格式
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            logger.warning("電子郵件驗證失敗 - 格式不正確: " + email);
            return false;
        }
        
        // 驗證密碼強度
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            logger.warning("密碼驗證失敗 - 強度不足");
            return false;
        }
        
        return true;
    }
    
    /**
     * 檢查客戶名稱是否存在
     * @param custName 客戶名稱
     * @return 是否存在
     */
    private boolean isCustomerNameExists(String custName) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM customers WHERE cust_name = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
            pstmt.setString(1, custName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    /**
     * 檢查電子郵件是否存在
     * @param email 電子郵件
     * @return 是否存在
     */
    private boolean isEmailExists(String email) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM customers WHERE email = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    /**
     * 生成客戶編號
     * @return 新的客戶編號
     */
    private int generateCustomerNumber() throws SQLException {
        String selectSQL = "SELECT MAX(cust_num) FROM customers";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxNum = rs.getInt(1);
                return maxNum + 1;
            }
        }
        return 1; // 第一個客戶
    }
    
    /**
     * 生成商品編號
     * @return 新的商品編號
     */
    private int generateProductNumber() throws SQLException {
        String selectSQL = "SELECT MAX(prod_num) FROM products";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxNum = rs.getInt(1);
                return maxNum + 1;
            }
        }
        return 1; // 第一個商品
    }
    
    /**
     * 生成訂單編號
     * @return 新的訂單編號
     */
    private int generateOrderNumber() throws SQLException {
        String selectSQL = "SELECT MAX(ord_num) FROM orders";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxNum = rs.getInt(1);
                return maxNum + 1;
            }
        }
        return 1001; // 第一個訂單
    }
    
    /**
     * 密碼加密
     * @param password 原始密碼
     * @return 加密後的密碼
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.severe("密碼加密失敗: " + e.getMessage());
            throw new RuntimeException("密碼加密失敗", e);
        }
    }
    
    /**
     * 驗證密碼
     * @param password 原始密碼
     * @param hashedPassword 加密後的密碼
     * @return 驗證結果
     */
    private boolean verifyPassword(String password, String hashedPassword) {
        try {
            // 使用簡單的字符串比較（測試用）
            return password.equals(hashedPassword);
        } catch (Exception e) {
            logger.warning("密碼驗證失敗: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 驗證商品資料
     * @param productData 商品資料
     * @return 驗證結果
     */
    private boolean validateProductData(Map<String, Object> productData) {
        // 檢查必要欄位
        if (productData.get("prod_name") == null || productData.get("prod_name").toString().trim().isEmpty()) {
            logger.warning("商品名稱驗證失敗 - 不能為空");
            return false;
        }
        
        if (productData.get("prod_type") == null || productData.get("prod_type").toString().trim().isEmpty()) {
            logger.warning("商品類型驗證失敗 - 不能為空");
            return false;
        }
        
        if (productData.get("prod_line") == null || productData.get("prod_line").toString().trim().isEmpty()) {
            logger.warning("商品系列驗證失敗 - 不能為空");
            return false;
        }
        
        if (productData.get("prod_price") == null) {
            logger.warning("商品價格驗證失敗 - 不能為空");
            return false;
        }
        
        return true;
    }
    
    /**
     * 驗證訂單資料
     * @param orderData 訂單資料
     * @return 驗證結果
     */
    private boolean validateOrderData(Map<String, Object> orderData) {
        // 檢查必要欄位
        if (orderData.get("cust_num") == null) {
            logger.warning("客戶編號驗證失敗 - 不能為空");
            return false;
        }
        
        if (orderData.get("order_date") == null || orderData.get("order_date").toString().trim().isEmpty()) {
            logger.warning("訂單日期驗證失敗 - 不能為空");
            return false;
        }
        
        if (orderData.get("county") == null || orderData.get("county").toString().trim().isEmpty()) {
            logger.warning("縣市驗證失敗 - 不能為空");
            return false;
        }
        
        return true;
    }
    
    /**
     * 檢查商品是否存在
     * @param prodNum 商品編號
     * @return 是否存在
     */
    private boolean isProductExists(int prodNum) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM products WHERE prod_num = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
            pstmt.setInt(1, prodNum);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    /**
     * 檢查商品是否有相關訂單
     * @param prodNum 商品編號
     * @return 是否有相關訂單
     */
    private boolean hasRelatedOrders(int prodNum) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM order_detail WHERE prod_num = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
            pstmt.setInt(1, prodNum);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    /**
     * 獲取商品總數量
     * @param prodType 商品類型
     * @param prodLine 商品系列
     * @param status 狀態
     * @return 總數量
     */
    private int getProductCount(String prodType, String prodLine, String status) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) FROM products WHERE 1=1 ");
        
        List<Object> parameters = new ArrayList<>();
        
        if (prodType != null && !prodType.isEmpty()) {
            sqlBuilder.append("AND prod_type = ? ");
            parameters.add(prodType);
        }
        
        if (prodLine != null && !prodLine.isEmpty()) {
            sqlBuilder.append("AND prod_line = ? ");
            parameters.add(prodLine);
        }
        
        if (status != null && !status.isEmpty()) {
            sqlBuilder.append("AND status = ? ");
            parameters.add(status);
        }
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
    
    /**
     * 獲取訂單總數量
     * @param custNum 客戶編號
     * @param status 狀態
     * @return 總數量
     */
    private int getOrderCount(Integer custNum, String status) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) FROM orders WHERE 1=1 ");
        
        List<Object> parameters = new ArrayList<>();
        
        if (custNum != null) {
            sqlBuilder.append("AND cust_num = ? ");
            parameters.add(custNum);
        }
        
        if (status != null && !status.isEmpty()) {
            sqlBuilder.append("AND status = ? ");
            parameters.add(status);
        }
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
    
    /**
     * 驗證圖片檔案
     * @param fileName 檔案名
     * @param fileSize 檔案大小
     * @return 驗證結果
     */
    private boolean validateImageFile(String fileName, long fileSize) {
        // 檢查檔案大小
        if (fileSize > MAX_FILE_SIZE) {
            logger.warning("圖片檔案過大: " + fileSize + " bytes");
            return false;
        }
        
        // 檢查檔案擴展名
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (extension.equals(allowedExt)) {
                return true;
            }
        }
        
        logger.warning("不支援的檔案格式: " + extension);
        return false;
    }
    
    /**
     * 生成唯一檔案名
     * @param originalFileName 原始檔案名
     * @return 唯一檔案名
     */
    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf((int) (Math.random() * 1000));
        return timestamp + "_" + random + extension;
    }
    
    /**
     * 更新主圖片狀態
     * @param prodNum 商品編號
     * @param isMain 是否為主圖片
     */
    private void updateMainImageStatus(int prodNum, boolean isMain) throws SQLException {
        String updateSQL = "UPDATE product_images SET is_main = ? WHERE prod_num = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSQL)) {
            pstmt.setBoolean(1, isMain);
            pstmt.setInt(2, prodNum);
            pstmt.executeUpdate();
        }
    }
    
    /**
     * 刪除商品圖片檔案
     * @param prodNum 商品編號
     */
    private void deleteProductImages(int prodNum) {
        try {
            String selectSQL = "SELECT image_path FROM product_images WHERE prod_num = ?";
            try (PreparedStatement pstmt = getConnection().prepareStatement(selectSQL)) {
                pstmt.setInt(1, prodNum);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String imagePath = rs.getString("image_path");
                        try {
                            Files.deleteIfExists(Paths.get(imagePath));
                            logger.info("刪除圖片檔案: " + imagePath);
                        } catch (IOException e) {
                            logger.warning("刪除圖片檔案失敗: " + imagePath + " - " + e.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("查詢商品圖片失敗: " + e.getMessage());
        }
    }
    
    /**
     * 創建上傳目錄
     */
    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("創建上傳目錄: " + UPLOAD_DIR);
            }
        } catch (IOException e) {
            logger.severe("創建上傳目錄失敗: " + e.getMessage());
        }
    }
    
    // =============================================
    // 資源清理
    // =============================================
    
    /**
     * 關閉資料庫連接
     */
    public void close() {
        try {
            if (connection != null && !getConnection().isClosed()) {
                getConnection().close();
                logger.info("資料庫連接已關閉");
            }
        } catch (SQLException e) {
            logger.severe("關閉資料庫連接時發生錯誤: " + e.getMessage());
        }
    }
    
    // =============================================
    // 主方法 - 用於測試
    // =============================================
    
    /**
     * 主方法 - 用於測試服務功能
     * @param args 命令行參數
     */
    public static void main(String[] args) {
        // 設置日誌級別
        Logger.getLogger("").setLevel(Level.INFO);
        
        // 創建服務實例
        EcommerceService service = new EcommerceService();
        
        try {
            // 測試客戶註冊
            logger.info("=== 測試客戶註冊 ===");
            Map<String, Object> registerResult = service.registerCustomer(
                "測試客戶", 
                "test@example.com", 
                "Test123!@#", 
                "0912345678",
                "台北市信義區信義路五段7號"
            );
            logger.info("註冊結果: " + registerResult);
            
            // 測試客戶登錄
            logger.info("=== 測試客戶登錄 ===");
            Map<String, Object> loginResult = service.loginCustomer("測試客戶", "Test123!@#");
            logger.info("登錄結果: " + loginResult);
            
            // 測試新增商品
            logger.info("=== 測試新增商品 ===");
            Map<String, Object> productData = new HashMap<>();
            productData.put("prod_name", "測試商品");
            productData.put("prod_type", "電子產品");
            productData.put("prod_line", "測試系列");
            productData.put("prod_price", 1000);
            productData.put("description", "這是一個測試商品");
            productData.put("stock_quantity", 10);
            productData.put("status", "active");
            
            Map<String, Object> addProductResult = service.addProduct(productData);
            logger.info("新增商品結果: " + addProductResult);
            
        } catch (Exception e) {
            logger.severe("測試過程中發生錯誤: " + e.getMessage());
        } finally {
            // 關閉服務
            service.close();
        }
    }
}
