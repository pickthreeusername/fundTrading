//倒计时 20s
function countDown(id){
		    var code = $(id);
		    code.attr("disabled","disabled");
		    code.addClass("disable_btn")
		    var time = 60;
		    var set=setInterval(function(){
		    code.val("("+--time+")秒后重新获取");
		    }, 1000);
		    setTimeout(function(){
		    code.attr("disabled",false).val("重新获取验证码").removeClass("disable_btn").addClass("getCodeOrange");
		    clearInterval(set);
		    }, 60000);
		}
//发送验证码
function sendPhoneCode(phone, countDownId,tipId){
	$(tipId).html("");
	$.ajax({
		url:"/customer/user/sendCode.do",
		type:"post",
		data: {"phone":phone},
		success: function(msg){
			if (msg == 1){
				countDown(countDownId);
			}else if(msg == -1){
				$(tipId).html("操作频繁，请稍后再试");
			}else if(msg == 0){
				$(tipId).html("验证码发送失败");
			}
		}
	})
}
//核对手机验证码
function verifyPhoneCode(phone,code,tipId,hideObj,showObj,url){
	$.ajax({
		url : "/customer/user/checkPhoneCode.do",
		type : "post",
		data : {"phone":phone, "phoneCode":code },
		success : function(msg){
			if (msg == 1){
				//正确
				if (hideObj != "0"){
					$(hideObj).hide();
					$(showObj).show();
				}
				if (url !="0"){
					location.href = url;
				}
			}else{
				$(tipId).html("短信验证码无效！")
			}
		}
	})
}
//1后跳转安全管理首页
function toSecurityMg(setOrAlter){
	var url;
	if (setOrAlter == "set"){
		//设置交易密码 成功后跳转路径
		url = "/customer/bank/toAdd.do";
	}else{
		url = "/customer/security/toSecurityMg.do";
	}
	setTimeout(function(){
		location.href = url;
	},500);
}
//更新 登录 或交易密码
function updateCode(choice, hideObj, setOrAlter){
	$("#newPwdTip").html("");
	$("#rePwdTip").html("");
	var newPwd = $("#newPwd").val();
	var rePwd = $("#rePwd").val();
	//密码正则
	var regPwd;
	var tip;
	var url;
	if (choice == "password"){
		regPwd = /^[0-9a-zA-Z_]{6,18}$/;
		tip = "密码为6-18位数字字母下划线组合";
		url = "/customer/security/updatePwd.do";
	}else{
		regPwd = /^\w{8}$/;
		tip = "交易密码为8位阿拉伯数字";
		url = "/customer/security/updateTransCode.do";
	}
	
	if (newPwd == "" || regPwd.test(newPwd) == false){
		$("#newPwdTip").html(tip);
	}else if (rePwd == ""){
		$("#rePwdTip").html("请确认密码");
	}else if (newPwd != rePwd){
		$("#rePwdTip").html("密码不一致");
	}else{
		$.ajax({
			url : url,
			type : "post",
			data : {"pwd":newPwd},
			success : function(msg){
				if (msg == 1){
					//更新成功
					$(hideObj).hide();
					$("#success").show();
					toSecurityMg(setOrAlter);
				}else{
					alert("update failed");
				}
			}
			
		})
	}
}