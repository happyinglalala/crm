layui.use(['table','layer','form'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        form = layui.form,
        $ = layui.jquery,
        table = layui.table;

    form.on('submit(addOrUpdateCustomer)',function (data){
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx + "/customer/add";
        if($('[name="id"]').val()){
            url=ctx+"/customer/update";
        }
        $.post(url,data.field,function (result){
            if(result.code===200){
                layer.msg("操作成功！！！");
                layer.close(index);
                layer.closeAll("iframe");
                parent.location.reload();
            }else {
                layer.msg(result.msg);
                top.layer.close(index);
            }
        });
    })





    /*form.on('submit(addOrUpdateCustomer)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx+"/customer/save";
        if($("input[name='id']").val()){
            url=ctx+"/customer/update";
        }
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
    });*/


});