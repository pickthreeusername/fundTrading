
$(function(){
	$("#add").click(function(){
		location.href = "add.jsp";
	});
	//批量删除
	$("#deleteAll").click(function(){
		deleteBooks()	
	})
	//全选
	$("input[name='cbox']").click(function(){
		if ($("input[name='cbox']:checked").length == $("input[name='cbox']").length){
			$("input[name='selectAll']").prop("checked", true);
		}else{
			$("input[name='selectAll']").prop("checked", false);
		}
	})
	
})
function deleteStu(id,ele){
	var flag = confirm("确定删除？");
	if (flag){
		$.ajax({
			url:"stu/deleteStu.do",
			type:"post",
			data:{"stuId":id},
			success:function(row){
				if(row == 1){
					$(ele).parent().parent().remove();
				}else{
					alert("删除失败");
				}
			}
		});
	}
	
}
function modify(stuId){
	location.href="stu/toDetails.do?op=byTeacher&stuId=" + stuId;
}
function verified(id,ele,op){
	var eleName = $(ele).attr("name");
	var ifVerified;
	var path;
	var paramName;
	if (op =="stu"){
		path = "stu/verified.do";
	}else{
		path = "emp/verified.do";
	}
	if (eleName == "verified_btn"){
		ifVerified = "是";
	}else{
		ifVerified = "否";
	}
	$.ajax({
		url:path,
		type:"post",
		data:{id:id,verified:ifVerified},
		success:function(data){
			if(data == 1){
				$(ele).parent().parent().remove();
			}else{
				alert("操作失败");
			}
		}
	});
}
function selectAll(ele){
	$("input[name='cbox']").each(function(){
		if($(ele).prop("checked")){
			$(this).prop("checked",true);
		}else{
			$(this).prop("checked", false);
		}
	})
}
