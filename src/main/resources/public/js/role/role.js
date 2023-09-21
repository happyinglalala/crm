layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    //用户角色列表展示
    var  tableIns = table.render({
        // 设置table的id属性值
        // id属性值
        elem: '#roleList',
        url : ctx+'/role/list',
        // 单元格最小宽度
        cellMinWidth : 95,
        // 开启分页
        page : true,
        // 容器高度
        height : "full-125",
        // 每页页数的可选项
        limits : [10,15,20,25],
        // 默认每页显示的数量
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true",align: "center"},
            {field: 'roleName', title: '机会来源',align:"center"},
            {field: 'roleRemark', title: '客户名称',  align:'center'},
            {field: 'createDate', title: '成功几率', align:'center'},
            {field: 'updateDate', title: '概要', align:'center'},
            {title: '操作', templet:'#roleListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });


    $(".search_btn").click(function (){
        tableIns.reload({
            where:{//设置接口的额外参数
                roleName:$("[name='roleName']").val(),
            },
            page: {
                curr:1
            }
        });
    });


    table.on("toolbar(roles)",function (data){
        if(data.event=="add"){
        // 打开添加和修改角色的页面
            openAddOrUpdate();
        }else if(data.event=="grant"){
        //     授权操作
            var checkStatus = table.checkStatus(data.config.id);
        //     打开授权的对话框
            openAddGrantDialog(checkStatus.data);
        }
    });

    function openAddGrantDialog(data){
        if(data.length==0){
            layer.msg("请选择要授权的角色！",{icon:5});
            return;
        }
        if(data.length>1){
            layer.msg("暂不支持批量授权！",{icon:5});
            return;
        }
        var url = ctx + "/model/toAddGrantPage?roleId="+data[0].id;
        var title = "<h1>角色管理 - 角色授权</h1>";
        layui.layer.open({
            title:title,
            content: url,
            type:2,
            area:["600px","200px"],
            minmax:true
        });
    }

    function openAddOrUpdate(roleId){
        var title="添加角色";
        var url = ctx + "/role/toAddOrUpdateRole";
        if(roleId){
            title = "更新角色";
            url+="?roleId="+roleId;
        }
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['500px', '250px'],
            content: url, //iframe的url
            minmax:true
        });
    };



    table.on("tool(roles)",function (data){
        var roleId = data.data.id;
        if(data.event=="edit"){
            openAddOrUpdate(roleId);
        }else if(data.event=="del"){
            deleteRole(roleId);
        }
    });


    function deleteRole(roleId) {
        layer.confirm("你确定要删除选中的角色吗???",{icon:3,title:"角色管理"},function (index) {
            layer.close(index);
            $.ajax({
                type: "put",
                url: ctx + "/role/delete",
                data: {
                    roleId: roleId
                },
                success: function (result) {
                    if (result.code == 200) {
                        layer.msg("删除成功！！");
                        tableIns.reload();
                    } else {
                        layer.msg(result.msg,{icon:5});
                    }
                }
            });
        });
    }

});