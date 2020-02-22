//提交评论
function postcomment(e) {
	var content;
	var id = e.getAttribute("data-id");
	var type = e.getAttribute("data-type");

	if (type == 1) {
		content = $("#content").val();
	} else {
		content = $("#input-" + id).val();
	}
	$.ajax({
		type: "POST",
		url: "/comment",
		contentType: 'application/json',
		data: JSON.stringify({
			"parentid": id,
			"text": content,
			"type": type
		}),
		success: function (response) {
			if (response.message == "用户未登录") {
				var conf = confirm(response.message);
				if (conf) {
					window.localStorage.setItem("close", true);
					window.open("https://github.com/login/oauth/authorize?client_id=16153c947bee8283a03e&redirect_uri=http://localhost:9999/callback&scope=user&state=m");
				}
			} else {
				alert(response.message);
				window.location.reload();
			}
		},
		dataType: "json"
	});
}

//展开二级评论
function collapseComments(e) {
	var id = e.getAttribute("data-id");
	var comment = $("#comment-" + id);
	
	if (!comment.hasClass("in")&&comment.children().length==1) {	
		$.getJSON("/comment/" + id, function (data) {
			$.each(data.data.reverse(), function (index,commentDTO) {				
				var mediabody=$("<div/>",{
					"class":"media-body"
				}).append($("<h3/>",{
					"class":"media-heading",
					"text":commentDTO.commentator.name
				})).append($("<div/>",{
					"class":"menu",
					"text":commentDTO.comment.text
				})).append($("<span/>",{
					"class":"pull-right",
					"text":timeStamp2String(commentDTO.comment.gmtcreate)
				})).append($("<hr>"));
				
				var media=$("<div/>",{
					"class":"media"
				}).append(mediabody);
				comment.prepend(media);
			});
		});
	}
	comment.toggleClass("in");
}

function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
}

function thumbsup(e){
	var id = e.getAttribute("data-id");
	var type = e.getAttribute("data-type");
	$.ajax({
		type: "POST",
		url: "/thumbsup",
		contentType: 'application/json',
		data: JSON.stringify({
			"id": id,
			"type": type
		}),
		success: function (response) {
			if (response.message == "用户未登录") {
				var conf = confirm(response.message);
				if (conf) {
					window.localStorage.setItem("close", true);
					window.open("https://github.com/login/oauth/authorize?client_id=16153c947bee8283a03e&redirect_uri=http://localhost:9999/callback&scope=user&state=m");
				}
			} else {
				alert(response.message);
				window.location.reload();
			}
		},
		dataType: "json"
	});
}
