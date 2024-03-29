//随机更变验证码
function changeVerifyCode(img) {
    img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}

//name为传进来的key
function getQuerySting(name) {
    var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//reg匹配url的参数名
    if (r!=null)
    {
        return decodeURIComponent(r[2]);//取出后面的shopId的值
    }
    return '';
}
//将日期按照格式化输出
Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 由于在properties中设置了项目的路径 /o2o 导致在配置静态资源的时候，少了一个前缀
 * 这里要将其加上去
 **/
function getContextPath() {
    return "/o2o";
}