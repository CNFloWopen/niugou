$(function() {
	//绑定账号的controller
	var bindUrl = '/o2o/local/bindlocalauth';
	//从地址栏的url获取usertype
	//usertype=1则为前端展示系统，其余为店家管理系统
	var usertype = getQuerySting("usertype");
	$('#submit').click(function() {
		//获取输入的账号和密码
		var userName = $('#username').val();
		var password = $('#psw').val();
		//获取输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		//访问后台，绑定账号
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					if (usertype == 1)
					{
						//若用户在前端展示系统页面则自动退回到前端展示系统首页
                        window.location.href = '/o2o/frontend/index';
					}else {
						//若用户是在店家管理系统页面则自动回退到店铺列表页中
						window.location.href = '/o2o/shopadmin/shoplist';
					}

				} else {
					$.toast('绑定失败！');
					$('#captcha_img').click();
				}
			}
		});
	});
});