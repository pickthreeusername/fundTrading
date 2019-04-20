$(function(){
	$("#confirm").click(function(){
		var mobile = $("#mobilePhone").val().trim();
		var pwd = $("#pwd").val();
		var flagMobile = true;
		var flagPwd = true;
		var tip;
		if (mobile == ""){
			tip = "请输入登录手机号";
			flagMobile = false;
		}else if (pwd == ""){
			tip = "请输入密码";
			flagPwd = false;
		}else{
			tip = "";
		}
		$("#tips").html(tip);
		if (flagMobile && flagPwd){
			$.ajax({
				url:"/customer/user/login.do",
				type:"post",
				data:{"mobilePhone":$("#mobilePhone").val(),"password":$("#pwd").val()},
				success:function(result){
					if (result == 1){
						location.href="/customer/toMyAsset.do";
					}else{
						$("#tips").html("账号或密码错误！");
					}
				}
			});
		}
		
	})
})
	



	
	
	
