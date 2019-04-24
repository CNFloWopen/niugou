$(function() {
	// 登录验证
	var loginUrl = '/o2o/local/logincheck';
	//从地址栏的url获取usertype，usertype=1为customer，其他的为shopowner
	var usertype = getQuerySting("usertype");
	//登录次数，累计登录失败三次后，弹验证码出来要求输入
	var loginCount = 0;

	$('#submit').click(function() {
		//获取账号密码和验证信息
		var userName = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		//是否需要验证码验证，默认为false
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				//失败3次后，开启验证码验证
				needVerify = true;
			}
		}
		//访问后台进行登录验证
		$.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                userName : userName,
                password : password,
                verifyCodeActual : verifyCodeActual,
                needVerify : needVerify
            },
            success : function(data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (usertype==1)
                    {
                        window.location.href = '/o2o/frontend/index';
                    }else {
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast('登录失败！');
                    loginCount++;
                    if (loginCount >= 3) {
                        //失败三次后，需要验证
                        $('#verifyPart').show();
                    }
                }
            }
        });
	});

    $('#register').click(function() {
        window.location.href = '/o2o/local/register';
    });
});