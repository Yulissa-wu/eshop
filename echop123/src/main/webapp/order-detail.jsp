<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單明細</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">

    <h2>訂單明細</h2>
    <a href="${pageContext.request.contextPath}/orders/list" class="btn btn-secondary mb-3">← 回訂單列表</a>

    <p><strong>訂單編號:</strong> ${order.orderId}</p>
    <p><strong>建立時間:</strong> ${order.createdAt}</p>
    <p><strong>總金額:</strong> NT$ ${order.totalAmount}</p>

    <table class="table table-bordered align-middle">
        <thead>
            <tr>
                <th>商品圖片</th>
                <th>商品名稱</th>
                <th>單價</th>
                <th>數量</th>
                <th>小計</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="detail" items="${details}">
                <tr>
                    <td><img src="${pageContext.request.contextPath}/${detail.imagePath}" width="80"></td>
                    <td>${detail.productName}</td>
                    <td>NT$ ${detail.price}</td>
                    <td>${detail.quantity}</td>
                    <td>NT$ ${detail.subtotal}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
