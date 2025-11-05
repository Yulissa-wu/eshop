<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - 商品詳情</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .back-btn {
            background: rgba(255,255,255,0.2);
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
            border: 1px solid rgba(255,255,255,0.3);
        }
        
        .back-btn:hover {
            background: rgba(255,255,255,0.3);
            transform: translateY(-2px);
        }
        
        .product-detail {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 0;
            min-height: 500px;
        }
        
        .product-image-section {
            background: linear-gradient(45deg, #f8f9fa, #e9ecef);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 60px;
        }
        
        .product-image {
            width: 100%;
            max-width: 400px;
            height: 400px;
            background: white;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 8em;
            color: #667eea;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        
        .product-info-section {
            padding: 60px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        
        .product-category {
            display: inline-block;
            background: #e9ecef;
            color: #495057;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 0.9em;
            margin-bottom: 20px;
            width: fit-content;
        }
        
        .product-name {
            font-size: 2.5em;
            font-weight: 700;
            color: #333;
            margin-bottom: 20px;
            line-height: 1.2;
        }
        
        .product-price {
            font-size: 2em;
            font-weight: 700;
            color: #667eea;
            margin-bottom: 30px;
        }
        
        .product-description {
            color: #666;
            font-size: 1.1em;
            line-height: 1.6;
            margin-bottom: 30px;
        }
        
        .product-stock {
            font-size: 1.1em;
            color: #28a745;
            font-weight: 600;
            margin-bottom: 30px;
            padding: 15px;
            background: #d4edda;
            border-radius: 10px;
            border-left: 4px solid #28a745;
        }
        
        .action-buttons {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 15px 30px;
            border: none;
            border-radius: 25px;
            font-size: 1.1em;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
        }
        
        .btn-secondary {
            background: #f8f9fa;
            color: #495057;
            border: 2px solid #dee2e6;
        }
        
        .btn-secondary:hover {
            background: #e9ecef;
            transform: translateY(-2px);
        }
        
        .product-specs {
            background: #f8f9fa;
            padding: 30px;
            border-top: 1px solid #e9ecef;
        }
        
        .specs-title {
            font-size: 1.3em;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
        }
        
        .specs-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }
        
        .spec-item {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        
        .spec-label {
            font-weight: 600;
            color: #666;
            margin-bottom: 5px;
        }
        
        .spec-value {
            color: #333;
            font-size: 1.1em;
        }
        
        @media (max-width: 768px) {
            .product-detail {
                grid-template-columns: 1fr;
            }
            
            .product-image-section {
                padding: 30px;
            }
            
            .product-info-section {
                padding: 30px;
            }
            
            .product-name {
                font-size: 2em;
            }
            
            .product-price {
                font-size: 1.5em;
            }
            
            .action-buttons {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="/products" class="back-btn">← 返回商品列表</a>
            <h1>商品詳情</h1>
        </div>
        
        <c:if test="${not empty product}">
            <div class="product-detail">
                <div class="product-image-section">
                    <div class="product-image">
                        📦
                    </div>
                </div>
                
                <div class="product-info-section">
                    <span class="product-category">${product.category}</span>
                    <h1 class="product-name">${product.name}</h1>
                    <div class="product-price">NT$ ${product.price}</div>
                    <p class="product-description">${product.description}</p>
                    <div class="product-stock">
                        ✅ 庫存充足 (${product.stock} 件)
                    </div>
                    
                    <div class="action-buttons">
                        <button class="btn btn-primary" onclick="addToCart()">🛒 加入購物車</button>
                        <button class="btn btn-secondary" onclick="addToWishlist()">❤️ 加入願望清單</button>
                    </div>
                </div>
            </div>
            
            <div class="product-specs">
                <h3 class="specs-title">商品規格</h3>
                <div class="specs-grid">
                    <div class="spec-item">
                        <div class="spec-label">商品編號</div>
                        <div class="spec-value">#${product.id}</div>
                    </div>
                    <div class="spec-item">
                        <div class="spec-label">商品分類</div>
                        <div class="spec-value">${product.category}</div>
                    </div>
                    <div class="spec-item">
                        <div class="spec-label">商品價格</div>
                        <div class="spec-value">NT$ ${product.price}</div>
                    </div>
                    <div class="spec-item">
                        <div class="spec-label">庫存數量</div>
                        <div class="spec-value">${product.stock} 件</div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
    
    <script>
        function addToCart() {
            alert('商品已加入購物車！');
        }
        
        function addToWishlist() {
            alert('商品已加入願望清單！');
        }
    </script>
</body>
</html>