
// 頁面載入時取得產品列表
$(document).ready(function() {
   loadProducts();
  
   // 表單提交事件
   $('#productForm').submit(function(e) {
       e.preventDefault();//用來取消事件的預設行為，阻止表單提交後重新載入頁面。
       saveProduct();
   });
});
// 載入所有產品
function loadProducts() {
   $.ajax({
       url: '/api/products',
       method: 'GET',
       success: function(products) {
           renderProducts(products);
       },
       error: function(xhr) {
           showError('取得產品列表失敗: ' + xhr.statusText);
       }
   });
}
// 儲存產品 (新增/更新)
function saveProduct() {
   const product = {
       name: $('#name').val(),
       description: $('#description').val(),
       price: parseFloat($('#price').val()) || 0 // 避免 NaN
   };
   const productId = $('#productId').val();
   const url = productId ? `/api/products/${productId}` : '/api/products';
   const method = productId ? 'PUT' : 'POST';
   // 清空之前的錯誤訊息
   clearErrors();
   $.ajax({
       url: url,
       method: method,
       contentType: 'application/json',
       data: JSON.stringify(product),
       success: function() {
           showSuccess(productId ? '產品更新成功！' : '產品新增成功！');
           clearForm();
           loadProducts();
       },
       error: function(xhr) {
           if (xhr.status === 400) {
               // 顯示驗證錯誤
               const errors = xhr.responseJSON;
               showValidationErrors(errors);
           } else {
               showError('操作失敗: ' + xhr.statusText);
           }
       }
   });
}
// 顯示驗證錯誤
function showValidationErrors(errors) {
   for (const [field, message] of Object.entries(errors)) {
       let fieldElement = $(`#${field}`);
       if (fieldElement.length) {
           // 在輸入框下方顯示錯誤訊息
           fieldElement.after(`<div class="error-message text-danger">${message}</div>`);
           fieldElement.addClass('is-invalid');
       } else {
           // 全局錯誤顯示
           $('#errorContainer').append(`<div class="alert alert-danger">${field}: ${message}</div>`);
       }
   }
}
// 清空錯誤訊息
function clearErrors() {
   $('.error-message').remove();
   $('.is-invalid').removeClass('is-invalid');
   $('#errorContainer').empty();
}
// 顯示成功訊息
function showSuccess(message) {
   $('#successContainer').html(`
       <div class="alert alert-success alert-dismissible fade show">
           ${message}
           <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
       </div>
   `);
   // 3秒後自動消失
   setTimeout(() => $('#successContainer').empty(), 3000);
}
// 顯示錯誤訊息
function showError(message) {
   $('#errorContainer').html(`
       <div class="alert alert-danger alert-dismissible fade show">
           ${message}
           <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
       </div>
   `);
}
// 刪除產品
function deleteProduct(id) {
   if (confirm('確定要刪除此產品？')) {
       $.ajax({
           url: `/api/products/${id}`,
           method: 'DELETE',
           success: function() {
               showSuccess('產品刪除成功！');
               loadProducts();
           },
           error: function(xhr) {
               showError('刪除失敗: ' + xhr.statusText);
           }
       });
   }
}
// 清空表單
function clearForm() {
   $('#productForm')[0].reset();
   $('#productId').remove();
   $('#formTitle').text('新增產品');
   $('#submitBtn').text('儲存');
}
// 渲染產品列表
function renderProducts(products) {
   const tbody = $('#productList').empty();
   if (products.length === 0) {
       tbody.append('<tr><td colspan="4" class="text-center">暫無產品資料</td></tr>');
       return;
   }
  
   products.forEach(product => {
       tbody.append(`
           <tr>
               <td>${escapeHtml(product.name)}</td>
               <td>${escapeHtml(product.description)}</td>
               <td>$${product.price.toFixed(2)}</td>
               <td>
                   <button class="btn btn-sm btn-warning" onclick="editProduct(${product.id})">編輯</button>
                   <button class="btn btn-sm btn-danger ms-2" onclick="deleteProduct(${product.id})">刪除</button>
               </td>
           </tr>
       `);
   });
}
// 編輯產品 (帶入表單)
function editProduct(id) {
   $.get(`/api/products/${id}`)
       .done(function(product) {
           $('#name').val(product.name);
           $('#description').val(product.description);
           $('#price').val(product.price);
           $('<input>').attr({
               type: 'hidden',
               id: 'productId',
               value: id
           }).appendTo('#productForm');
           $('#formTitle').text('編輯產品');
           $('#submitBtn').text('更新');
           $('html, body').animate({ scrollTop: $('#productForm').offset().top }, 500);
       })
       .fail(function(xhr) {
           showError('取得產品資料失敗: ' + xhr.statusText);
       });
}
// 防止 XSS 的 HTML 跳脫
function escapeHtml(unsafe) {
   return unsafe?.toString()
       .replace(/&/g, "&amp;")
       .replace(/</g, "&lt;")
       .replace(/>/g, "&gt;")
       .replace(/"/g, "&quot;")
       .replace(/'/g, "&#039;") || '';
}

