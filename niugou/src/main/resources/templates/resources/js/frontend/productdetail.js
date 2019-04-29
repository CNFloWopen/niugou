$(function() {
	var productId = getQuerySting('productId');
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	$.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							var product = data.product;
							$('#product-img').attr('src', getContextPath()+product.imgAddr);
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#product-name').text(product.productName);
							$('#product-desc').text(product.productDesc);
							if (product.normalPrice!=undefined && product.promotionPrice != undefined)
							{
							//	如果原价和现价都不为空
								$('#price').show();
								$('#normalPrice').html(
									'<del>' + '¥' +product.normalPrice+'</del>'
								);
								$('#promotionPrice').text('¥'+product.promotionPrice)

							}
							var imgListHtml = '';
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div valign="bottom" class="card-header color-white no-border no-padding"> <img class="card-cover" src="'
										+getContextPath()+ item.imgAddr + '"/></div>';
							});
							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
