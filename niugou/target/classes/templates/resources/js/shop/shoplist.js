$(function() {
    //e是event 事件
    getlist();
   function getlist(e) {
       $.ajax({
           url:"/o2o/shopadmin/getshoplist",
           type:"get",
           dataType:"json",
           //data:---------....
           //第一个data就是发送给服务端的数据,success里面的data是服务器返回的数据
           success:function (data) {
               if (data.success){
                   handleList(data.shopList);//获取店铺列表
                   handleUser(data.user); //获取用户名


               }
           }
       });
   }
   function handleUser(data) {
       //显示用户的名字
       $('#user-name').text(data.name);
   }
    //获取列表里面的下拉列表或列表时都可以这样使用
    // 获取店铺的列表
    /**
     * item为当前项，即当前遍历的元素本身。分别为a, b, c
     * index为元素处于数组中的下标或索引。分别为 0, 1, 2
     * array为数组本身。值为['a', 'b', 'c']
     */
   function handleList(data) {
       var html = '';
       var shopList = data.shopList;
       data.map(function (item,index) {



           html += '<div class="row row-shop"><div class="col-40">'
               +item.shopName+'</div><div class="col-20">'+shopStatus(item.enableStatus)
               +'</div><div class="col-20">'+goShop(item.enableStatus,item.shopId)+'</div>'+textOp(item.enableStatus,item.shopId)
               +'</div>';
       });
       $('.shop-wrap').html(html);
   }
   //获取输出"优质店铺  "
    function shopStatus(status) {
        if (status == 0)
        {
            return '<font style="color:red;">已删除</font>';
        }else if(status==1) {
            return '运行';
        }
    }

    //进入店铺列表中，就是进入管理店铺的页面,id===--shopId
    // function goShop(id) {
    //         // 进入到商铺的管理页面,请求/shopadmin/shopmanagement ，进入到管理页面
    //         return '<a href="/o2o/shopadmin/shopmanagement?shopId='+id +'">进入</a>';
    // }
    function goShop(status,id) {
        //当状态是审核通过的话
        if (status == 1)
        {
            // 进入到商铺的管理页面,请求/shopadmin/shopmanagement ，进入到管理页面
            return '<a href="/o2o/shopadmin/shopmanagement?shopId='+id +'">进入</a>';
        }else {
            return '<font style="color: coral">无法操作</font>';
        }
    }

    function textOp(status,id)
    {
        if (status == 1)
        {
            return '<a href="#" class="status col-20" data-id="'
                + id
                + '">删除</a>';
        }else if(status==0) {
            return '';
        }
    }

    $('.shop-wrap')
        .on(
            'click',
            '.status',
        function (e) {
        var target = e.currentTarget;
        $.confirm("删除将无法复原,确定要删除吗?", function(){
                $.ajax({
                    url: '/o2o/shopadmin/delshop',
                    type: 'post',
                    data: {
                        shopId: target.dataset.id //target.dataset.id存的是shopId
                    },
                    dataType: 'json',
                    success:function (data) {
                        if (data.success) {
                            $.toast('删除成功');
                            getlist();
                        } else {
                            $.toast('删除失败');
                        }

                    }
                })
            }
        )
    })
});