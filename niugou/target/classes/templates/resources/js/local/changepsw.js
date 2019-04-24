$(function() {
	//修改密码
	var url = '/o2o/local/changelocalpwd';
    //从地址栏的url获取usertype，usertype=1为customer，其他的为shopowner
    var usertype = getQuerySting("usertype");
	$('#submit').click(function() {
		//获取账号密码和新密码
		var userName = $('#userName').val();
		// var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword)
		{
			$.toast("2次输出的新密码不一致")
		}
		//添加表单数据
		var formData = new FormData();
		formData.append('userName', userName);
		// formData.append('password', password);
		formData.append('newPassword', newPassword);
		//获取验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		//将参数post到后台去修改密码
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
                    if (usertype==1)
                    {
                        window.location.href = '/o2o/frontend/index';
                    }else {
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/o2o/local/shoplist';
	});
});
