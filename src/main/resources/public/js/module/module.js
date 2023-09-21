layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery,
        table = layui.table,
        treeTable = layui.treetable;
    // 渲染表格
      treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#munu-table',
        url: ctx+'/model/list',
        toolbar: "#toolbarDemo",
        treeDefaultClose:true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if(d.grade==1){
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 180, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    table.on("toolbar(munu-table)",function (data){
        if(data.event=="expand"){
            // 全部展开
            treeTable.expandAll("#munu-table");
        }else if(data.event=="fold"){
            // 全部折叠
            treeTable.foldAll("#munu-table");
        }else if(data.event=="add"){
        //     添加层级为0，父菜单为-1
            openAddModuleDialog(0,-1);
        }
    });

    // 监听行工具栏
    table.on('tool(munu-table)',function (data){
        if(data.event=="add"){
        //     添加子项
            if(data.data.grade==2){
                layer.msg("第三级菜单不能添加！！！",{icon:5});
                return;
            }
            openAddModuleDialog(data.data.grade + 1,data.data.id);
        }else if(data.event=="edit"){
        //     修改资源
            openUpdateModuleDialog(data.data.id);
        }else if(data.event=="del"){
            var roleId = data.data.id;
        //     删除资源
            layer.confirm("你确定要删除选中的权限吗???",{icon:3,title:"权限管理"},function (index) {
                layer.close(index);
                $.ajax({
                    type: "put",
                    url: ctx + "/model/delete",
                    data: {
                        roleId: roleId
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            layer.msg("删除成功！！");
                            window.location.reload();
                        } else {
                            layer.msg(result.msg,{icon:5});
                        }
                    }
                });
            });
        }
    })

    // 打开添加资源的对话框
    function openAddModuleDialog(grade,parentId){
        var title="<h1>资源管理 - 添加资源</h1>";
        var url = ctx + "/model/toAddModulePage?grade="+grade+"&parentId="+parentId;
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['700px', '450px'],
            content: url, //iframe的url
            minmax:true
        });
    };

    function openUpdateModuleDialog(id){
        var title="<h1>资源管理 - 修改资源</h1>";
        var url = ctx + "/model/toUpdateModulePage?id="+id;
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['700px', '450px'],
            content: url, //iframe的url
            minmax:true
        });
    }


});