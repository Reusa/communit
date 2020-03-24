function post() {
    var questionId = $('#question_id').val();
    var content = $("#comment_content").val();

    comment2target(questionId,1,content);
}

function comment2target(targetId,type,content) {
    if (!content){
        alert("请先输入回复");
        return
    }

    $.ajax({
        type:"POST",
        url: "/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function(response){
            if (response.code == 200){
                window.location.reload();
            } else {
                if (response.code == 2003){
                    var isAccept = confirm(response.message);
                    if (isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=2f16d286ce7f2a319476&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

//二级评论事件
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();

    comment2target(commentId,2 ,content);
}

//展开二级回复
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active")
    } else {
        $.getJSON("/comment/"+id,function (data) {
            var commentBody = $("comment-body-"+id);
            var items = [];

            $.each(data.data,function (comment) {
                var c = $("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html:comment.content
                });
                items.push(c);
            });

            commentBody.append($("<div/>",{
                "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comment",
                "id":"comment-"+id,
                html:items.join("")
            }));

            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active")
        })
        //展开

    }

}