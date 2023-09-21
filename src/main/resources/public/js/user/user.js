layui.use(['table','layer',"form"],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        id : "userTable",
        elem: '#userList',
        url : ctx+'/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '手机号', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });


    $(".search_btn").click(function (){
        tableIns.reload({
            where:{//设置接口的额外参数
                userName:$("[name='userName']").val(),//用户名称
                email:$("[name='email']").val(),//邮箱
                phone:$("[name='phone']").val()//手机号码
            },
            page: {
                curr:1
            }
        });
    });

    // 监听头部工具栏
    table.on('toolbar(users)',function (data){

        if(data.event=='add'){//添加用户
        //     打开添加或修改的对话框
            openAddOrUpdateUserDialog();
        }else if(data.event=='del'){//删除用户
            // 获取被选中的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus.data);
            deleteUsers(checkStatus.data);
        }

    });


    function deleteUsers(userData){
        if(userData.length == 0){
            layer.msg("请选择要删除的记录！！！",{icon:5});
            return;
        }
        layer.confirm("你确定要删除选中的记录吗???",{icon:3,title:"用户管理"},function (index){
            layer.close(index);
            var ids = "ids=";
            for(var i = 0;i<userData.length;i++){
                if(i<userData.length-1){
                    ids = ids + userData[i].id +"&ids=";
                }else {
                    ids = ids + userData[i].id;
                }
            }
            console.log(ids);
            $.ajax({
                type:"put",
                url:ctx+"/user/deleteUser",
                data:ids,
                success(result){
                    if(result.code==200){
                        layer.msg("删除成功！！");
                        tableIns.reload();
                    }else {
                        layer.msg(result.msg);
                    }
                }
            });
        });
    }

    function openAddOrUpdateUserDialog(id){
        var title = "<h3>用户管理 - 添加用户</h3>";
        var url = ctx + "/user/toAddOrUpdateUserPage"
        if(id != null && id != ''){
            title="<h3>用户管理 - 更新用户</h3>";
            url+= "?id="+id;
        }
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['650px', '400px'],
            content: url, //iframe的url
            maxmin:true
        });
    };


    // 监听行工具栏
    table.on('tool(users)',function (data){

        if(data.event=='edit'){//添加用户
            //     打开添加或修改的对话框
            openAddOrUpdateUserDialog(data.data.id);
        }else if(data.event=='del'){//删除用户
            deleteUser(data.data.id);
        }

    });

    function deleteUser(id){
        layer.confirm("你确定要删除选中的记录吗???",{icon:3,title:"用户管理"},function (index) {
            layer.close(index);
            $.ajax({
                type: "put",
                url: ctx + "/user/deleteUser",
                data: {
                    ids: id
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
