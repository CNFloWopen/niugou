$(function() {
	//从地址栏的url里获取productId
	var productId = getQuerySting('productId');
	//获取商品信息的url
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	//访问后台获取该商品的信息并渲染
	$.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							//获取商品信息
							var product = data.product;
							//给商品信息相关的html控件赋值
							//商品缩略图
							$('#product-img').attr('src', getContextPath()+product.imgAddr);
							//商品更新时间
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#product-name').text(product.productName);
							$('#product-desc').text(product.productDesc);
							if (product.normalPrice!=undefined && product.promotionPrice != undefined)
							{
							//	如果原价不为空而现价为空则只是显示原价
								$('#price').show();
								$('#normalPrice').html(
									'<del>' + '¥' +product.normalPrice+'</del>'
								);
								//如果现价不为空而原价为空则只是显示现价
								$('#promotionPrice').text('¥'+product.promotionPrice)

							}
							var imgListHtml = '';
							//遍历商品详情图列表，并批量生成img标签
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div> <img  src="'
										+getContextPath()+ item.imgAddr + '" width="100%" /></div>';
							});

							//判断顾客是否已经登录，登录显示二维码，没登录不显示
							if (data.needQRCode)
							{
							//	若顾客登录了
								imgListHtml += '<div><img src="/o2o/frontend/generateqrcode4sproduct?productId=' +
									product.productId+'" width="100%"></div>';
							}

							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
