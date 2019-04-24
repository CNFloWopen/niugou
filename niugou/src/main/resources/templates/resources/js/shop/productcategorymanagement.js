$(function()
{
    var listUrl = '/o2o/shopadmin/getproductcategorylist';
    var addUrl = '/o2o/shopadmin/addproductcategorys';
    var deleteUrl = '/o2o/shopadmin/removeproductcategory';
    getList();
    function getList(){
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success)
                {
                    var dataList = data.data;
                    $('.category-wrap').html('');
                    var tempHtml='';
                    dataList.map(function (item,index) {
                        //用now代表这个是原本添加完了的店铺
                        tempHtml +=''
                            + '<div class="row row-product-category now">'
                            + '<div class="col-33 row-product-category-name">'
                            +item.productCategoryName
                            +'</div>'
                            +'<div class="col-33">'
                            +item.priority
                            +'</div>'
                            +'<div class="col-33"><a href="#" class="button delete" data-id="'
                            +item.productCategoryId+'">删除</a></div>'
                            +'</div>';
                    });
                    $('.category-wrap').append(tempHtml);
                }
            });
    }
    $('#new')
        .click(
            function () {
                //用temp来代表这个是新加上来的店铺
                var tempHtml = '<div class="row row-product-category temp">'
                +'<div class="col-33"><input type="text" class="category-input category" placeholder="分类名"></div>'
                +'<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
                +'<div class="col-33"><a href="#" class="button delete">删除</a></div>'
                +'</div>';
                $('.category-wrap').append(tempHtml);
            });
    $('#submit').click(function () {
       var tempArr = $('.temp');
       var productCategoryList = [];
       tempArr.map(function (index,item) {
          var tempObj = {};
          tempObj.productCategoryName = $(item).find('.category').val(); //通过于html中的class对应，取到用户输入的信息，并赋值
          tempObj.priority = $(item).find('.priority').val();
          if (tempObj.productCategoryName && tempObj.priority)
          {
              productCategoryList.push(tempObj); //把获取到的值给到productCategoryList中
              $.toast('提交成功');
          }else {
              $.toast('提交失败');
          }
       });




       $.ajax({
           url:addUrl,  //提交的路由 '/o2o/shopadmin/addproductcategorys'
           type:'post',
           data: JSON.stringify(productCategoryList), //变成字符串的形式传到后台去
           contentType: 'application/json',
            success:function (data) {
                    if(data.success())
                    {
                        $.toast('提交成功');
                        getList(); //成功后返回商铺列表
                    }else {
                        $.toast('提交失败');
                    }
            }


       });
    });
    $('.category-wrap').on('click','.row-product-category.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();
        }
    );
    $('.category-wrap').on('click','.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm("确定要删除吗?", function(){
                    $.ajax({
                        url: deleteUrl,
                        type: 'post',
                        data: {
                            productCategoryId: target.dataset.id //target.dataset.id存的是productCategoryId
                        },
                        dataType: 'json',
                        success:function (data) {
                            if (data.success) {
                                $.toast('删除成功');
                                getList();
                            } else {
                                $.toast('删除失败');
                            }

                        }
                    })
                }
            )
        }
    )
});