layui.use(['table','layer','form'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        form = layui.form,
        $ = layui.jquery,
        table = layui.table;

    //关闭弹出框
    $(".layui-btn-normal").click(function (){
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    form.on('submit(addOrUpdateRole)',function (data){
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var formData = data.field;
        var url = ctx + "/role/add";
        if($("[name='id']").val()){
            url =  ctx + "/role/update";
        }
        $.post(url,formData,function (res) {
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







});