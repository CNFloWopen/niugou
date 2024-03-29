$(function() {
	var loading = false;
	//分页允许的最大返回条数，超过此数则禁止访问后台
	var maxItems = 20;
	////默认一页的返回商品数
	var pageSize = 10;
	//列出商品列表的url
	var listUrl = '/o2o/frontend/listproductsbyshop';
	//默认的页码
	var pageNum = 1;
	//从地址栏获取shopId
	var shopId = getQuerySting('shopId');
	var productCategoryId = '';
	var productName = '';
	//获取本店店铺信息以及商品信息列表的url
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='
			+ shopId;
    //渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    //预先加载10条商品信息
    addItems(pageSize, pageNum);



    /**
	 * 获取本店铺信息以及商品类别信息列表
     */
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src',getContextPath()+ shop.shopImg);
								$('#shop-update-time').html(
										new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(shop.shopName);
								$('#shop-desc').html(shop.shopDesc);
								$('#shop-addr').html(shop.shopAddr);
								$('#shop-phone').html(shop.phone);
								//从后台返回该店铺的商品列表
								var productCategoryList = data.productCategoryList;
								var html = '';
								//遍历商品列表，生成可以提供搜索相应商品类别下商品的a标签
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								//将商品类别a标签绑定在相应的html组件中
								$('#shopdetail-button-div').html(html);
							}
						});
	}


    /**
	 * 获取分页展示的商品列表信息
     * @param pageSize
     * @param pageIndex
     */
	function addItems(pageSize, pageIndex) {
        // 拼接出查询的url，赋空值默认就去掉这个条件的限制，有值就代表这个条件去进行筛选
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
        //设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
                //访问后台获取相应查询条件下的商品列表
				maxItems = data.count;
				var html = '';
                //遍历商品列表，拼接出卡片集合
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath()+item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")  //格式化Date.prototype.Format = function(fmt)
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
                //将卡片集合添加到目标html组件中去
				$('.list-div').append(html);
                //获取目前为止以显示的卡片总数，包含已经加载的
				var total = $('.list-div .card').length;
                //若总数达到根按照此查询条件列出来的总数一致，则停止后台加载
				if (total >= maxItems) {
                    /**
					 * 这里有bug，因为注销掉加载提示符了，会导致加载一次之后出现搜索加载不出下拉列表的商品或店铺
					 * 加载完毕，则注销无限加载事件，以防不必要的加载
                     $.detachInfiniteScroll($('.infinite-scroll'));
                     	删除加载提示符
                     $('.infinite-scroll-preloader').remove();
                     */

					// 达到上限时隐藏加载提示符
					$('.infinite-scroll-preloader').hide();
				}else {
                    $('.infinite-scroll-preloader').show();
				}
                //否则页码加一
				pageNum += 1;
                //加载结束可以再次加载了
				loading = false;
                //刷新页面显示新加载的店铺
				$.refreshScroller();
			}
		});
	}


    /**
     * 下滑屏幕自动进行分页搜索
     */
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

    /**
	 * 	选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
     */
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});
    /**
	 * 点击商品卡片进入商品详情页
     */
	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var productId = e.currentTarget.dataset.productId;
						window.location.href = '/o2o/frontend/productdetail?productId='
								+ productId;
					});
    /**
     * 需要查询的商品名字发生变化后，重置页码，清空原先查询的商品列表，按照现有条件进行查询
     */
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
    /**
     * 点击后打开右侧栏
     */
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
    //初始化页面
	$.init();
});
