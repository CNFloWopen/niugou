// 公用请求类
function f_request(options) {
    // 创建一个 Promise 对象，处理公共的请求操作
    return new Promise((resolve, reject) => {
        $.ajax({
            url: options.url,
            type: options.method || 'POST',
            data: JSON.stringify(options.data) || {},
            contentType:'application/json;charset=utf-8',
            dataType: 'json',
            success: res => {
                // 判断当前code 是否是正确，不正确则返回。
                let state = res.state;
                if (!state) {
                    reject(res);
                    return;
                }

                // 如果请求成功，则返回数据结果
                resolve(res.data);
            },
            error: err => {
                alert('服务器请求失败');
                reject(err)
            }
        })
    })
}