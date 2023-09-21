layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //关闭弹出框
    $("#closeBtn").click(function (){
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });
//     添加内容
    form.on('submit(addOrUpdateSaleChance)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx+"/sale_chance/add";
        var saleChanceId = $("[name='id']").val();
        if(saleChanceId != null && saleChanceId !=''){
            url = ctx + "/sale_chance/update";
        }
        console.log(data.field.cgjl + "111111111111");
        $.post(url,data.field,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功");
                top.layer.close(index);
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }else{
                layer.msg(res.msg);
            }
        });
        return false;
    });

    $.ajax({
        type:"get",
        url:ctx + "/user/queryAllSales",
        success:function (data){
            // console.log(data);
            if(data != null){
                var aManId = $("#aMan").val();
                for(var i = 0; i< data.length;i++){
                    var opt = "";
                    if(aManId == data[i].id){
                        opt = "<option value='"+data[i].id+"' selected = 'selected'>"+data[i].uname+"</option>";
                    }else {
                        opt = "<option value='"+data[i].id+"'>"+data[i].uname+"</option>";
                    }
                    // var opt= "<option value='"+data[i].id+"'>"+data[i].uname+"</option>";
                    $("#assignMan").append(opt);
                }
            }
            layui.form.render("select");
        }
    });



});
