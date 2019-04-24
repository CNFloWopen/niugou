/*
* ******************************************************************************************************
* ****************************************基本函数*********************************************
* ******************************************************************************************************
* */
let toast = new ToastClass();   // toast 对象
let selectShop = [];            // 当前选中的商品
let operateShop = undefined;    // 当前操作的商品
let shopCount = 5;              // 购物车商品的总长度
let shopData = [];              // 购物车数据

/**
 * @Description: 获取购物车列表
 * @date 2019-04-23  01:00
 */
function getShoppingCartList() {
    f_request({
        url: '/o2o/shoppingCar/list',
        data: {
            userId: 7
        }
    }).then(res => {
     shopData = res;
     initShoppingCartData(res);
    })
}
getShoppingCartList();

/**
 * @Description: 初始化购物车数据
 * @date 2019-04-22  23:05
 *
 * @param shopData: 购物车数据
 */
function initShoppingCartData(shopData) {
    let storeHtml = ``;

    for (let store of shopData) {
        storeHtml += ` <!--商店Item-->
        <div class="store-item" data-store-id="${store.productId}">
            <!--商店名称-->
            <div class="store-name">
                ${store.shopName}
            </div>
            ${initShopList(store.productInfoList)}
        </div>`
    }

    $('#shop-list').html(storeHtml);
}

/**
 * @Description: 初始化商品列表
 * @date 2019-04-22  23:04
 *
 * @param shopList 初始化 商品列表
 */
function initShopList(shopList) {
    let shopHtml = ``;
    for (let shop of shopList) {
        shopHtml += ` 
            <!--商品 Item-->
            <div class="shop-item">
                <!--选择商品-->
                <div data-shop-id="${shop.productId}" class="checkbox sel-shop">
                   <span class="sel-y glyphicon glyphicon-ok-circle">
                   </span>
                </div>

                <!--商品内容-->
                <div class="shop-content">
                    <!--商品图片-->
                    <img class="shop-img" src="${shop.productImgAddr}">
                    <!--商品信息-->
                    <div class="shop-info">
                        <div class="shop-present">${shop.productName}</div>
                        <!--商品数量-->
                        <div class="row">
                            <div class="col-xs-6 shop-price">
                                ￥ ${shop.productPrice}
                            </div>
                            <div class="col-xs-6">
                                <div style="float: right;">
                                    <span data-shop-id="${shop.productId}" class="bt-edit-shop glyphicon glyphicon-pencil" onclick="edit(event)"></span>
                                    <div class="shop-count">
                                        <span class="bt-minus glyphicon glyphicon-minus"></span>
                                        <span class="num">${shop.productCount}</span>
                                        <span class="bt-plus glyphicon glyphicon-plus"></span>
                                     </div>
                                     <div class="operate-bt" style="display: none">
                                         <button class="bt-delete-shop btn btn-danger btn-xs" onclick="remove(event)">删除</button>
                                         <button class="bt-edit-success btn btn-success btn-xs" onclick="finish(event)">完成</button>
                                     </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>`
    }
    return shopHtml;
}

/**
 * @Description: 根据ID 获取当前的 商品
 * @date 2019-04-23  00:30
 *
 *@param productId: 商品Id
 */
function getShop(productId) {
    // 获取当前编辑的商品数据
    productId = parseInt(productId);   // 商品ID
    for (let store of shopData) {
        let product = store.productInfoList.find(item => item.productId === productId);
        if (product) return product;
    }
    return undefined;
}

/*
* ******************************************************************************************************
* ****************************************所有事件*********************************************
* ******************************************************************************************************
* */

//选择所有商品
function selectAllShop(event) {
    // 如果购物车长度与选择商品长度一样，则表示当前为取消全选操作
    if (selectShop.length === shopCount) {
        selectShop = [];
        $('#select-shop-all').removeClass('active');
        $('.shop-item .sel-shop').removeClass('active');
    } else {
        shopData.forEach(item => {
            selectShop = selectShop.concat(item.shop);
        });
        $('.shop-item .sel-shop').addClass('active');
        $('#select-shop-all').addClass('active');
    }
}

//商品 Item
let shopItem = $('.shop-item');
//选择商品
shopItem.on('click', '.sel-shop', function (event) {
    event.stopPropagation();
    let shop = getShop(event.currentTarget.dataset.productId);
    if (shop) {
        //移除商品全选状态
        $('#select-shop-all').removeClass('active');

        let shopInx = selectShop.findIndex(item => item.productId === shop.id);
        // 如果不等于 -1 ，则表示当前已经选中了商品，则移除。
        if (shopInx !== -1) {
            $(this).removeClass('active');
            selectShop.splice(shopInx, 1);
        } else {
            $(this).addClass('active');
            selectShop.push(shop);
        }

        // 初始化是否全部选择状态
        if (selectShop.length === shopCount) {
            $('#select-shop-all').addClass('active');
        }
    }
});

//点击编辑按钮
function edit(event) {
    event.stopPropagation();
    $(this).hide();
    $(this).siblings('.shop-count').show();    // 显示编辑数量按钮
    $(this).siblings('.operate-bt').show();     // 显示操作按钮
    operateShop = getShop(event.target.dataset.productId); // 获取当前选中的
};

// 编辑商品完成
function finish(event) {
    f_request({
        url: '/o2o/shoppingCar/update',
        data: {
            shoppingCarId: operateShop.shoppingCarId,
            productCount: operateShop.productCount
        }
    }).then(res => {
        $(this).parent().siblings('.bt-edit-shop').show();   // 显示编辑按钮
        $(this).parent().siblings('.shop-count').hide();// 隐藏编辑数量容器
        $(this).parent().hide();                    // 隐藏操作按钮容器
        operateShop = undefined;                    // 复位选择操作的商品
    })
};

// 删除商品
function remove(event) {
    f_request({
        url: '/o2o/shoppingCar/remove',
        data: {
            shoppingCarId: operateShop.shoppingCarId
        }
    }).then(res => {
        operateShop = undefined;                    // 复位选择操作的商品
        getShoppingCartList();
    });
};

//点击商品数量加
shopItem.on('click', '.shop-count .bt-plus', function (event) {
    event.stopPropagation();
    // 设置数量
    if (operateShop) {
        operateShop.productCount++;
        $(this).siblings('.num').text(operateShop.productCount)
    }
});

//点击商品数量减
shopItem.on('click', '.shop-count .bt-minus', function (event) {
    event.stopPropagation();
    // 设置数量
    if (operateShop) {
        operateShop.productCountproductCount--;
        if (operateShop.productCount < 1) {
            toast.show({
                text: '已到最小值',
            });
            operateShop.productCount = 1; // 复位数量，因为当前已经减少了。
            return;
        }
        $(this).siblings('.num').text(operateShop.productCount)
    }
});