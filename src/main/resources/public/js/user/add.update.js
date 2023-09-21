layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var  formSelects = layui.formSelects;

    //关闭弹出框
    $("#closeBtn").click(function (){
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    form.on('submit(addOrUpdateUser)',function (data){
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var formData = data.field;
        var url = ctx + "/user/addUser";
        if($("[name='id']").val()){
            url = ctx + "/user/updateUser";
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


    formSelects.config("selectId",{
        type:"post",
        searchUrl:ctx+"/role/queryAllRoles?userId="+$("[name='id']").val(),//请求地址
        keyName: "roleName",//下拉框的文本内容，与返回的数据key一致
        keyVal: "id",
    },true);

});