$(function() {
    var shopId = getQuerySting('shopId');
    var isEdit = shopId ? true:false;
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var shopInfoUrl="/o2o/shopadmin/getshopbyid?shopId="+shopId;
    var editShopUrl = '/o2o/shopadmin/modifyshop';
    // alert(initUrl);
    if (!isEdit)
    {
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        //data是获取josn的返回的数据
        $.getJSON(shopInfoUrl,function (data) {
            if (data.success)
            {
                //获取shop的值
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="'+shop.shopCategory.shopCategoryId+'" selected>'
                        +shop.shopCategory.shopCategoryName +'</option>';
                var tempAreaHtml = "";
                data.areaList.map(function (item,index) {
                    tempAreaHtml += '<option data-id="'+item.areaId+'">'
                        +item.areaName +'</option>';
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');//不让用户操作
                //加入到html页面中
                $('#area').html(tempAreaHtml);
                //默认选择区域现在的信息
                $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
            }
        });
        }


    /**
     * item为当前项，即当前遍历的元素本身。分别为a, b, c
     * index为元素处于数组中的下标或索引。分别为 0, 1, 2
     * array为数组本身。值为['a', 'b', 'c']
     */
    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success)
            {
                var tempHtml = "";
                var tempAreaHtml = "";
                data.shopCategoryList.map(function (item,index) {
                    tempHtml += '<option data-id="'+item.shopCategoryId+'">'
                        +item.shopCategoryName +'</option>';
                });
                data.areaList.map(function (item,index) {
                    tempAreaHtml += '<option data-id="'+item.areaId+'">'
                        +item.areaName +'</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }
    $('#submit').click(function () {
        var shop = {};
        if (isEdit)
        {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        // if (shop.phone)
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId : $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId : $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shop-img')[0].files[0];
        // 接收数据
        var formData = new FormData();
        // 和后端约定好，利用shopImg和 shopStr接收 shop图片信息和shop信息
        formData.append('shopImg',shopImg);
        //获取shop中的所有信息通过====shopStr，这是和后端约定好的
        // 转成JSON格式，后端收到后将JSON转为实体类
        formData.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual)
        {
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        // 利用ajax提交
        $.ajax({
            url : (isEdit?editShopUrl:registerShopUrl),
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.success){
                    $.toast('提交成功!');
                }else {
                    $.toast('提交失败!'+data.errMsg);
                }
                // 点击提交后 不管成功失败都更换验证码，防止重复提交
                $('#captcha_img').click();
            }
        });
    });
});