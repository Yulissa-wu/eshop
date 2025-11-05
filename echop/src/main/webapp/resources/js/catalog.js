// =============================================
// 商品展示頁面 JavaScript
// 創建時間: 2024
// 功能: 商品展示、搜索、篩選、分頁
// =============================================

// =============================================
// 全域變數
// =============================================

// 頁面狀態變數
let currentPage = 1;
let pageSize = 12;
let currentView = 'list'; // 'grid' 或 'list'
let currentFilters = {
    search: '',
    category: '',
    status: '',
    sort: 'newest'
};

// API 基礎 URL
const API_BASE_URL = '/api';

// 日誌記錄器
const logger = {
    info: (message) => console.log(`[INFO] ${new Date().toISOString()} - ${message}`),
    warn: (message) => console.warn(`[WARN] ${new Date().toISOString()} - ${message}`),
    error: (message) => console.error(`[ERROR] ${new Date().toISOString()} - ${message}`)
};

// =============================================
// 頁面初始化
// =============================================

/**
 * 頁面載入完成後初始化
 */
document.addEventListener('DOMContentLoaded', function() {
    logger.info('商品展示頁面載入完成');
    
    try {
        // 初始化事件監聽器
        initializeEventListeners();
        
        // 載入分類選項
        loadCategories();
        
        // 載入商品列表
        loadProducts();
        
        logger.info('頁面初始化完成');
    } catch (error) {
        logger.error('頁面初始化失敗: ' + error.message);
        showErrorMessage('頁面初始化失敗，請重新載入頁面');
    }
});

// =============================================
// 事件監聽器初始化
// =============================================

/**
 * 初始化所有事件監聽器
 */
function initializeEventListeners() {
    logger.info('初始化事件監聽器');
    
    try {
        // 搜索按鈕點擊事件
        const searchBtn = document.getElementById('searchBtn');
        if (searchBtn) {
            searchBtn.addEventListener('click', function() {
                logger.info('搜索按鈕被點擊');
                performSearch();
            });
        }
        
        // 搜索框回車事件
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    logger.info('搜索框回車事件');
                    performSearch();
                }
            });
            
            // 搜索框輸入事件（實時搜索）
            searchInput.addEventListener('input', debounce(function() {
                logger.info('搜索框輸入事件');
                performSearch();
            }, 500));
        }
        
        // 分類篩選變更事件
        const categoryFilter = document.getElementById('categoryFilter');
        if (categoryFilter) {
            categoryFilter.addEventListener('change', function() {
                logger.info('分類篩選變更: ' + this.value);
                currentFilters.category = this.value;
                currentPage = 1;
                loadProducts();
            });
        }
        
        // 狀態篩選變更事件
        const statusFilter = document.getElementById('statusFilter');
        if (statusFilter) {
            statusFilter.addEventListener('change', function() {
                logger.info('狀態篩選變更: ' + this.value);
                currentFilters.status = this.value;
                currentPage = 1;
                loadProducts();
            });
        }
        
        // 排序選項變更事件
        const sortOptions = document.querySelectorAll('input[name="sortOption"]');
        sortOptions.forEach(function(radio) {
            radio.addEventListener('change', function() {
                logger.info('排序選項變更: ' + this.value);
                currentFilters.sort = this.value;
                currentPage = 1;
                loadProducts();
            });
        });
        
        // 視圖切換事件
        const gridView = document.getElementById('gridView');
        if (gridView) {
            gridView.addEventListener('click', function() {
                logger.info('切換到網格視圖');
                switchView('grid');
            });
        }
        
        const listView = document.getElementById('listView');
        if (listView) {
            listView.addEventListener('click', function() {
                logger.info('切換到列表視圖');
                switchView('list');
            });
        }
        
        // 加入購物車按鈕事件
        const addToCartBtn = document.getElementById('addToCartBtn');
        if (addToCartBtn) {
            addToCartBtn.addEventListener('click', function() {
                logger.info('加入購物車按鈕被點擊');
                handleAddToCart();
            });
        }
        
        logger.info('事件監聽器初始化完成');
    } catch (error) {
        logger.error('事件監聽器初始化失敗: ' + error.message);
    }
}

// =============================================
// 搜索功能
// =============================================

/**
 * 執行搜索
 */
function performSearch() {
    const searchInput = document.getElementById('searchInput');
    if (!searchInput) {
        logger.warn('搜索輸入框不存在');
        return;
    }
    
    const searchTerm = searchInput.value.trim();
    logger.info('執行搜索: ' + searchTerm);
    
    currentFilters.search = searchTerm;
    currentPage = 1; // 重置到第一頁
    loadProducts();
}

/**
 * 防抖函數 - 延遲執行函數
 * @param {Function} func 要執行的函數
 * @param {number} wait 延遲時間（毫秒）
 * @returns {Function} 防抖後的函數
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// =============================================
// 視圖切換
// =============================================

/**
 * 切換視圖模式
 * @param {string} view 視圖模式 ('grid' 或 'list')
 */
function switchView(view) {
    logger.info('切換視圖: ' + view);
    
    try {
        currentView = view;
        
        const productGrid = document.getElementById('productGrid');
        const productList = document.getElementById('productList');
        const gridBtn = document.getElementById('gridView');
        const listBtn = document.getElementById('listView');
        
        if (view === 'grid') {
            if (productGrid) productGrid.style.display = 'block';
            if (productList) productList.style.display = 'none';
            if (gridBtn) {
                gridBtn.classList.add('active');
                gridBtn.classList.remove('btn-outline-secondary');
                gridBtn.classList.add('btn-secondary');
            }
            if (listBtn) {
                listBtn.classList.remove('active');
                listBtn.classList.remove('btn-secondary');
                listBtn.classList.add('btn-outline-secondary');
            }
        } else {
            if (productGrid) productGrid.style.display = 'none';
            if (productList) productList.style.display = 'block';
            if (listBtn) {
                listBtn.classList.add('active');
                listBtn.classList.remove('btn-outline-secondary');
                listBtn.classList.add('btn-secondary');
            }
            if (gridBtn) {
                gridBtn.classList.remove('active');
                gridBtn.classList.remove('btn-secondary');
                gridBtn.classList.add('btn-outline-secondary');
            }
        }
        
        logger.info('視圖切換完成: ' + view);
    } catch (error) {
        logger.error('視圖切換失敗: ' + error.message);
    }
}

// =============================================
// 分類載入
// =============================================

/**
 * 載入商品分類選項
 */
function loadCategories() {
    logger.info('載入分類選項');
    
    fetch(`${API_BASE_URL}/categories`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            logger.info('分類資料載入成功: ' + JSON.stringify(data));
            
            const categorySelect = document.getElementById('categoryFilter');
            if (!categorySelect) {
                logger.warn('分類選擇器不存在');
                return;
            }
            
            // 清空現有選項（保留"所有分類"選項）
            categorySelect.innerHTML = '<option value="">所有分類</option>';
            
            // 添加分類選項
            if (data.success && data.categories) {
                data.categories.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.category_id;
                    option.textContent = category.category_name;
                    categorySelect.appendChild(option);
                });
                
                logger.info('分類選項添加完成，共 ' + data.categories.length + ' 個分類');
            }
        })
        .catch(error => {
            logger.error('載入分類失敗: ' + error.message);
            showErrorMessage('載入分類失敗，請稍後再試');
        });
}

// =============================================
// 商品載入
// =============================================

/**
 * 載入商品列表
 */
function loadProducts() {
    logger.info('載入商品列表 - 頁碼: ' + currentPage + ', 篩選條件: ' + JSON.stringify(currentFilters));
    
    try {
        // 顯示載入指示器
        showLoadingIndicator();
        
        // 構建查詢參數
        const params = new URLSearchParams({
            page: currentPage,
            pageSize: pageSize,
            search: currentFilters.search,
            category: currentFilters.category,
            status: currentFilters.status,
            sort: currentFilters.sort
        });
        
        // 發送 API 請求
        fetch(`${API_BASE_URL}/products?${params}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                logger.info('商品資料載入成功: ' + JSON.stringify(data));
                
                // 隱藏載入指示器
                hideLoadingIndicator();
                
                if (data.success) {
                    displayProducts(data.products);
                    updatePagination(data.total_pages, data.page);
                } else {
                    showNoProductsMessage();
                }
            })
            .catch(error => {
                logger.error('載入商品失敗: ' + error.message);
                hideLoadingIndicator();
                showErrorMessage('載入商品時發生錯誤，請稍後再試');
            });
    } catch (error) {
        logger.error('載入商品過程中發生錯誤: ' + error.message);
        hideLoadingIndicator();
        showErrorMessage('載入商品時發生錯誤，請稍後再試');
    }
}

/**
 * 顯示商品列表
 * @param {Array} products 商品陣列
 */
function displayProducts(products) {
    logger.info('顯示商品: ' + products.length + ' 個');
    
    try {
        if (products.length === 0) {
            showNoProductsMessage();
            return;
        }
        
        // 隱藏無商品訊息
        const noProductsMessage = document.getElementById('noProductsMessage');
        if (noProductsMessage) {
            noProductsMessage.style.display = 'none';
        }
        
        if (currentView === 'grid') {
            displayProductGrid(products);
        } else {
            displayProductList(products);
        }
        
        logger.info('商品顯示完成');
    } catch (error) {
        logger.error('顯示商品失敗: ' + error.message);
        showErrorMessage('顯示商品時發生錯誤');
    }
}

/**
 * 顯示商品網格視圖
 * @param {Array} products 商品陣列
 */
function displayProductGrid(products) {
    logger.info('顯示商品網格視圖');
    
    const gridContainer = document.getElementById('productGrid');
    if (!gridContainer) {
        logger.warn('商品網格容器不存在');
        return;
    }
    
    gridContainer.innerHTML = '';
    
    products.forEach(product => {
        const productCard = createProductCard(product);
        gridContainer.appendChild(productCard);
    });
}

/**
 * 顯示商品列表視圖
 * @param {Array} products 商品陣列
 */
function displayProductList(products) {
    logger.info('顯示商品列表視圖');
    
    const listContainer = document.getElementById('productList');
    if (!listContainer) {
        logger.warn('商品列表容器不存在');
        return;
    }
    
    listContainer.innerHTML = '';
    
    products.forEach(product => {
        const listItem = createProductListItem(product);
        listContainer.appendChild(listItem);
    });
}

// =============================================
// 商品卡片創建
// =============================================

/**
 * 創建商品卡片
 * @param {Object} product 商品物件
 * @returns {HTMLElement} 商品卡片元素
 */
function createProductCard(product) {
    logger.info('創建商品卡片: ' + product.product_name);
    
    const col = document.createElement('div');
    col.className = 'col-md-4 col-lg-3 mb-4';
    
    col.innerHTML = `
        <div class="card h-100 product-card">
            <div class="card-img-top-container">
                <img src="${product.main_image || '/resources/img/placeholder.jpg'}" 
                     class="card-img-top" 
                     alt="${product.product_name}"
                     onerror="this.src='/resources/img/placeholder.jpg'">
                <div class="card-overlay">
                    <button class="btn btn-primary btn-sm" onclick="viewProductDetail(${product.product_id})">
                        <i class="fas fa-eye"></i> 查看詳情
                    </button>
                </div>
            </div>
            <div class="card-body d-flex flex-column">
                <h5 class="card-title">${product.product_name}</h5>
                <p class="card-text text-muted small">${product.description || '暫無描述'}</p>
                <div class="mt-auto">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <span class="h5 text-danger">$${product.price}</span>
                        <span class="badge bg-primary">${product.category_name}</span>
                    </div>
                    <div class="d-flex justify-content-between">
                        <small class="text-muted">庫存: ${product.stock_quantity}</small>
                        <span class="badge ${getStatusBadgeClass(product.status)}">${getStatusText(product.status)}</span>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    return col;
}

/**
 * 創建商品列表項目
 * @param {Object} product 商品物件
 * @returns {HTMLElement} 商品列表項目元素
 */
function createProductListItem(product) {
    logger.info('創建商品列表項目: ' + product.product_name);
    
    const col = document.createElement('div');
    col.className = 'col-12 mb-3';
    
    col.innerHTML = `
        <div class="card product-list-item">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-2">
                        <img src="${product.main_image || '/resources/img/placeholder.jpg'}" 
                             class="img-fluid rounded" 
                             alt="${product.product_name}"
                             onerror="this.src='/resources/img/placeholder.jpg'">
                    </div>
                    <div class="col-md-8">
                        <h5 class="card-title">${product.product_name}</h5>
                        <p class="card-text">${product.description || '暫無描述'}</p>
                        <div class="d-flex align-items-center">
                            <span class="badge bg-primary me-2">${product.category_name}</span>
                            <span class="badge ${getStatusBadgeClass(product.status)}">${getStatusText(product.status)}</span>
                        </div>
                    </div>
                    <div class="col-md-2 text-end">
                        <div class="h4 text-danger mb-2">$${product.price}</div>
                        <div class="mb-2">
                            <small class="text-muted">庫存: ${product.stock_quantity}</small>
                        </div>
                        <button class="btn btn-primary btn-sm" onclick="viewProductDetail(${product.product_id})">
                            <i class="fas fa-eye"></i> 查看詳情
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    return col;
}

// =============================================
// 商品詳情
// =============================================

/**
 * 查看商品詳情
 * @param {number} productId 商品ID
 */
function viewProductDetail(productId) {
    logger.info('查看商品詳情: ' + productId);
    
    fetch(`${API_BASE_URL}/products/${productId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                showProductModal(data.product);
            } else {
                showErrorMessage('載入商品詳情失敗');
            }
        })
        .catch(error => {
            logger.error('載入商品詳情失敗: ' + error.message);
            showErrorMessage('載入商品詳情時發生錯誤，請稍後再試');
        });
}

/**
 * 顯示商品詳情模態框
 * @param {Object} product 商品物件
 */
function showProductModal(product) {
    logger.info('顯示商品詳情模態框: ' + product.product_name);
    
    try {
        // 填充模態框內容
        const elements = {
            'modalProductName': product.product_name,
            'modalProductTitle': product.product_name,
            'modalProductDescription': product.description || '暫無描述',
            'modalProductImage': product.main_image || '/resources/img/placeholder.jpg',
            'modalProductCategory': product.category_name,
            'modalProductStatus': getStatusText(product.status),
            'modalProductPrice': `$${product.price}`,
            'modalProductStock': product.stock_quantity,
            'modalProductBrand': product.brand || '暫無品牌',
            'modalProductSku': product.sku || '暫無SKU'
        };
        
        // 更新模態框元素
        Object.entries(elements).forEach(([id, value]) => {
            const element = document.getElementById(id);
            if (element) {
                if (id === 'modalProductImage') {
                    element.src = value;
                } else {
                    element.textContent = value;
                }
            }
        });
        
        // 處理標籤
        const tagsContainer = document.getElementById('modalProductTags');
        if (tagsContainer) {
            tagsContainer.innerHTML = '';
            if (product.tags) {
                const tags = product.tags.split(',').map(tag => tag.trim());
                tags.forEach(tag => {
                    const badge = document.createElement('span');
                    badge.className = 'badge bg-secondary me-1';
                    badge.textContent = tag;
                    tagsContainer.appendChild(badge);
                });
            }
        }
        
        // 顯示模態框
        const modal = new bootstrap.Modal(document.getElementById('productModal'));
        modal.show();
        
        logger.info('商品詳情模態框顯示完成');
    } catch (error) {
        logger.error('顯示商品詳情模態框失敗: ' + error.message);
        showErrorMessage('顯示商品詳情時發生錯誤');
    }
}

// =============================================
// 購物車功能
// =============================================

/**
 * 處理加入購物車
 */
function handleAddToCart() {
    logger.info('處理加入購物車');
    
    // 這裡可以實現加入購物車的邏輯
    showSuccessMessage('商品已加入購物車！');
}

// =============================================
// 分頁功能
// =============================================

/**
 * 更新分頁導航
 * @param {number} totalPages 總頁數
 * @param {number} currentPageNum 當前頁碼
 */
function updatePagination(totalPages, currentPageNum) {
    logger.info('更新分頁 - 總頁數: ' + totalPages + ', 當前頁: ' + currentPageNum);
    
    const pagination = document.getElementById('pagination');
    if (!pagination) {
        logger.warn('分頁容器不存在');
        return;
    }
    
    pagination.innerHTML = '';
    
    if (totalPages <= 1) return;
    
    try {
        // 上一頁按鈕
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPageNum === 1 ? 'disabled' : ''}`;
        prevLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPageNum - 1})">上一頁</a>`;
        pagination.appendChild(prevLi);
        
        // 頁碼按鈕
        for (let i = 1; i <= totalPages; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === currentPageNum ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#" onclick="changePage(${i})">${i}</a>`;
            pagination.appendChild(li);
        }
        
        // 下一頁按鈕
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPageNum === totalPages ? 'disabled' : ''}`;
        nextLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPageNum + 1})">下一頁</a>`;
        pagination.appendChild(nextLi);
        
        logger.info('分頁更新完成');
    } catch (error) {
        logger.error('更新分頁失敗: ' + error.message);
    }
}

/**
 * 切換頁面
 * @param {number} page 頁碼
 */
function changePage(page) {
    logger.info('切換到頁面: ' + page);
    
    if (page < 1) return;
    
    currentPage = page;
    loadProducts();
    
    // 滾動到頂部
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// =============================================
// UI 輔助函數
// =============================================

/**
 * 顯示載入指示器
 */
function showLoadingIndicator() {
    const loadingIndicator = document.getElementById('loadingIndicator');
    const productGrid = document.getElementById('productGrid');
    const productList = document.getElementById('productList');
    const noProductsMessage = document.getElementById('noProductsMessage');
    
    if (loadingIndicator) loadingIndicator.style.display = 'block';
    if (productGrid) productGrid.style.display = 'none';
    if (productList) productList.style.display = 'none';
    if (noProductsMessage) noProductsMessage.style.display = 'none';
}

/**
 * 隱藏載入指示器
 */
function hideLoadingIndicator() {
    const loadingIndicator = document.getElementById('loadingIndicator');
    if (loadingIndicator) loadingIndicator.style.display = 'none';
}

/**
 * 顯示無商品訊息
 */
function showNoProductsMessage() {
    const noProductsMessage = document.getElementById('noProductsMessage');
    const productGrid = document.getElementById('productGrid');
    const productList = document.getElementById('productList');
    
    if (noProductsMessage) noProductsMessage.style.display = 'block';
    if (productGrid) productGrid.style.display = 'none';
    if (productList) productList.style.display = 'none';
}

/**
 * 顯示錯誤訊息
 * @param {string} message 錯誤訊息
 */
function showErrorMessage(message) {
    logger.error('錯誤訊息: ' + message);
    
    // 創建錯誤提示元素
    const errorDiv = document.createElement('div');
    errorDiv.className = 'alert alert-danger alert-dismissible fade show position-fixed';
    errorDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    errorDiv.innerHTML = `
        <i class="fas fa-exclamation-triangle"></i>
        <span>${message}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(errorDiv);
    
    // 5秒後自動移除
    setTimeout(() => {
        if (errorDiv.parentNode) {
            errorDiv.parentNode.removeChild(errorDiv);
        }
    }, 5000);
}

/**
 * 顯示成功訊息
 * @param {string} message 成功訊息
 */
function showSuccessMessage(message) {
    logger.info('成功訊息: ' + message);
    
    // 創建成功提示元素
    const successDiv = document.createElement('div');
    successDiv.className = 'alert alert-success alert-dismissible fade show position-fixed';
    successDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    successDiv.innerHTML = `
        <i class="fas fa-check-circle"></i>
        <span>${message}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(successDiv);
    
    // 3秒後自動移除
    setTimeout(() => {
        if (successDiv.parentNode) {
            successDiv.parentNode.removeChild(successDiv);
        }
    }, 3000);
}

// =============================================
// 狀態輔助函數
// =============================================

/**
 * 獲取狀態徽章樣式
 * @param {string} status 狀態
 * @returns {string} CSS 類別名稱
 */
function getStatusBadgeClass(status) {
    switch (status) {
        case 'active': return 'bg-success';
        case 'inactive': return 'bg-danger';
        case 'draft': return 'bg-warning';
        default: return 'bg-secondary';
    }
}

/**
 * 獲取狀態文字
 * @param {string} status 狀態
 * @returns {string} 狀態文字
 */
function getStatusText(status) {
    switch (status) {
        case 'active': return '上架中';
        case 'inactive': return '已下架';
        case 'draft': return '草稿';
        default: return '未知';
    }
}

// =============================================
// 錯誤處理
// =============================================

/**
 * 全域錯誤處理器
 */
window.addEventListener('error', function(event) {
    logger.error('全域錯誤: ' + event.error.message);
    showErrorMessage('發生未預期的錯誤，請重新載入頁面');
});

/**
 * 未處理的 Promise 拒絕處理器
 */
window.addEventListener('unhandledrejection', function(event) {
    logger.error('未處理的 Promise 拒絕: ' + event.reason);
    showErrorMessage('網路請求失敗，請檢查網路連接');
});

// =============================================
// 工具函數
// =============================================

/**
 * 格式化日期
 * @param {string} dateString 日期字串
 * @returns {string} 格式化後的日期
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('zh-TW');
}

/**
 * 格式化價格
 * @param {number} price 價格
 * @returns {string} 格式化後的價格
 */
function formatPrice(price) {
    return new Intl.NumberFormat('zh-TW', {
        style: 'currency',
        currency: 'TWD'
    }).format(price);
}

/**
 * 截斷文字
 * @param {string} text 文字
 * @param {number} maxLength 最大長度
 * @returns {string} 截斷後的文字
 */
function truncateText(text, maxLength = 100) {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
}

// =============================================
// 頁面卸載清理
// =============================================

/**
 * 頁面卸載時清理資源
 */
window.addEventListener('beforeunload', function() {
    logger.info('頁面即將卸載，清理資源');
    
    // 清理定時器
    // 清理事件監聽器
    // 清理其他資源
});

logger.info('商品展示頁面 JavaScript 載入完成');


