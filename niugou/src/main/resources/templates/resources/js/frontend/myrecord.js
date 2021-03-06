$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;
	//获取顾客消费记录列表的URL
	var listUrl = '/o2o/frontend/listuserproductmapsbycustomer';

	var pageNum = 1;
	var productName = '';

    //按照查询条件获取消费记录列表并生成对应的html元素并添加到列表中
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?pageIndex=' + pageIndex
				+ '&pageSize=' + pageSize + '&productName=' + productName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				//获取总数
				maxItems = data.count;
				var html = '';
				data.userProductMapList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.shop.shopName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-inner">'
							+ '<div class="item-title"">' + item.product.productName
							+ '</div>' + '<div class="item-subtitle">'+'<font color="red" style="margin-left: 30%">'+item.product.promotionPrice+"元"+'</font>'+'</div>'+ '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.createTime).Format("yyyy-MM-dd")
							+ '</p>' + '</div>' + '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').remove();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}

    addItems(pageSize, pageNum);
	//滚动进行分页显示
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	//绑定搜索事件，主要按照产品名进行模糊查询
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	//点击我的，显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
