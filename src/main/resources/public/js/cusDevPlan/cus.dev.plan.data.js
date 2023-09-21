layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //计划项数据展示
    var  tableIns = table.render({
        id : "cusDevPlanListTable",
        elem: '#cusDevPlanList',
        url : ctx+'/cus_dev_plan/list?saleChanceId='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'planItem', title: '计划项',align:"center"},
            {field: 'exeAffect', title: '执行效果',align:"center"},
            {field: 'planDate', title: '执行时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#cusDevPlanListBar"}
        ]]
    });



    table.on("toolbar(cusDevPlans)",function (data){
       if(data.event == "add"){//添加计划向
            openAddOrUpdateCusDevPlanDialog();
       }else if(data.event == "success"){//开发成功
       //      更新营销机会的开发状态
           updateSaleChanceDevResult(2);
       }else if(data.event == "failed"){//开发失败
       //      更新营销机会的开发状态
           updateSaleChanceDevResult(3);
       }
    });

    // 监听行工具栏
    table.on('tool(cusDevPlans)',function (data){
        if(data.event=="edit"){
            openAddOrUpdateCusDevPlanDialog(data.data.id);
        }else if(data.event=="del"){
            // console.log(data.data.id);
            deleteCusDevPlan(data.data.id);
        }
    });

    // 删除计划项
    function deleteCusDevPlan(id){
        layer.confirm('您确认要删除吗？？？',{icon:3,title:'开发项数据管理'},function (index){
            $.ajax({
                type:"post",
                url:ctx + "/cus_dev_plan/delete",
                data:{
                    id:id
                },
                success:function (result){
                    if(result.code==200){
                        layer.msg("删除成功！！！",{icon:6});
                        tableIns.reload();
                    }else{
                        layer.msg(result.msg,{icon:5});
                    }
                }
            })
        });
    }

    function openAddOrUpdateCusDevPlanDialog(id) {
        // console.log(id)
        var title = "计划向管理 - 添加计划向";
        var url = ctx + "/cus_dev_plan/toAddOrUpdateCusDevPlanPage?sId="+$("[name='id']").val();
        if(id != null && id != ''){
            title = "计划向管理 - 更新计划向";
            url =url + "&id="+id;
        }
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['500px', '300px'],
            content: url, //iframe的url
            minmax:true
        });
    };


    function updateSaleChanceDevResult(devResult){
    //    弹出提示框提示信息
        layer.confirm("您确认执行该操作吗？？？",{icon:3,title:"营销机会管理"},function (index){
            var sId = $("[name='id']").val();
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/updateSaleChanceDevResult",
                data: {
                    id:sId,
                    devResult:devResult
                },
                success:function (result){
                    if(result.code==200){
                        layer.msg("修改成功！！！",{icon:6});
                        layer.closeAll("iframe");
                        parent.location.reload();
                    }else {
                        layer.msg(result.msg,{icon:5});
                    }
                }
            })
        });
    }




});
