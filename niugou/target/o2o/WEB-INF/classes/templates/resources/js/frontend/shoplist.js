$(function() {
	var loading = false;
	//分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 999;
	//一页返回的最大条数
	var pageSize = 5;
	//获取店铺列表的url
	var listUrl = '/o2o/frontend/listshops';
	//获取店铺类别列表以及区域列表的url
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	//页码
	var pageNum = 1;
	//从地址栏url里尝试获取parent shopCategory area id
	var parentId = getQuerySting('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	//渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();
    //预先加载10条店铺信息
	addItems(pageSize,pageNum);
    /**
	 * 获取店铺类别列表以及区域列表信息
     */
	function getSearchDivData() {
		//如果传入了parentId，则取出一级类别下面的所有二级类别
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$.getJSON(
						url,
						function(data) {
							if (data.success) {
								//获取后台返回过来的商铺类别列表
								var shopCategoryList = data.shopCategoryList;
								var html = '';
								html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
								//遍历商铺类别列表，拼接出a标签
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								//将拼接好的类别标签嵌入前台的html中
								$('#shoplist-search-div').html(html);
								var selectOptions = '<option value="">全部街道</option>';
								//获取后台返回过来的区域列表
								var areaList = data.areaList;
								//遍历区域列表信息，并拼接出option标签
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								//将标签集添加到area列表里
								$('#area-search').html(selectOptions);
							}
						});
	}

    /**
	 * 获取分页展示店铺列表信息
     * @param pageSize
     * @param pageIndex
     */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的url，赋空值默认就去掉这个条件的限制，有值就代表这个条件去进行筛选
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		//设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		//访问后台获取相应查询条件下的店铺列表
		$.getJSON(url, function(data) {
			if (data.success) {
				//获取当前查询条件下的店铺总数
				maxItems = data.count;
				var html = '';
				//遍历店铺列表，拼接出卡片集合
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath()+item.shopImg + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
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
                	//没有达到上限就show出来x
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
	//点击店铺卡片进入该店铺的详情页
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});
	//选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId) {// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					//若之前已经选定了别的category，则移除其选定的效果，改成选定新的
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					//由于查询条件改变，清空店铺列表在进行查询
					$('.list-div').empty();
					//重置页码
					pageNum = 1;
					addItems(pageSize, pageNum);
				} else {// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
					parentId = '';
				}

			});
    /**
	 * 需要查询的店铺名字发生变化后，重置页码，清空原先查询的店铺列表，按照现有条件进行查询
     */
	$('#search').on('change', function(e) { //第二个bug，由于上面这个input（on）方法太快而empty较慢会出现重复加载
		shopName = e.target.value;			//把input改成change
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
    /**
	 * 区域列表发生变化后，重置页码，清空原先的店铺列表，按照现有条件进行查询
     */
	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
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
