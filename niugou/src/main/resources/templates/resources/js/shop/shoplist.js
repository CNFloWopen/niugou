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
       data.map(function (item,index) {
           html += '<div class="row row-shop"><div class="col-40">'
               +item.shopName+'</div><div class="col-40">'+shopStatus(item.enableStatus)
               +'</div><div class="col-20">'+goShop(item.enableStatus,item.shopId)+'</div></div>';
       });
       $('.shop-wrap').html(html);
   }
   //获取店铺的状态值，看是否在审核中
    function shopStatus(status) {
        if (status == 0)
        {
            return '商家店铺';
        }else if(status==-1) {
            return '商家店铺';
        }else if (status==2) {
            return '商家店铺';
        }
    }

    //进入店铺列表中，就是进入管理店铺的页面,id===--shopId
    function goShop(status,id) {
       //当状态是审核通过的话
       //  if (status == 1)
       //  {
            // 进入到商铺的管理页面,请求/shopadmin/shopmanagement ，进入到管理页面
            return '<a href="/o2o/shopadmin/shopmanagement?shopId='+id +'">进入</a>';
        // }else {
        //     return '';
        // }
    }
});