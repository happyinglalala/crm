layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        // 设置table的id属性值
        // id属性值
        elem: '#saleChanceList',
        // 设置flag=1表示查询的是客户开发计划数据
        url : ctx+'/sale_chance/list?flag=1',
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
        id : "saleChanceListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'chanceSource', title: '机会来源',align:"center"},
            {field: 'customerName', title: '客户名称',  align:'center'},
            {field: 'cgjl', title: '成功几率', align:'center'},
            {field: 'overview', title: '概要', align:'center'},
            {field: 'linkMan', title: '联系人',  align:'center'},
            {field: 'linkPhone', title: '联系电话', align:'center'},
            {field: 'description', title: '描述', align:'center'},
            {field: 'createMan', title: '创建人', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '修改时间', align:'center'},
            {field: 'devResult', title: '开发状态', align:'center',templet:function (d){
                    return formaterDevResult(d.devResult);
                }},
            {title: '操作', templet:'#op',fixed:"right",align:"center", minWidth:150}
        ]]
    });


    function formaterDevResult(devResult){
        if(devResult == 0){
            return "<div style='color: grey'>未开发</div>";
        }else if(devResult == 1){
            return "<div style='color: #00FFFF'>开发中</div>";
        }else if(devResult == 2){
            return "<div style='color: red'>开发成功</div>";
        }else if(devResult == 3){
            return "<div style='color: purple'>开发失败</div>";
        }else {
            return "<div style='color: yellow'>未知</div>";
        }
    }



    $(".search_btn").click(function (){
        tableIns.reload({
            where:{//设置接口的额外参数
                customerName:$("[name='customerName']").val(),
                createMan:$("[name='createMan']").val(),
                devResult:$("#devResult").val()
            },
            page: {
                curr:1
            }
        });
    });



    table.on('tool(saleChances)',function (data){
        if(data.event == "dev"){/*开发*/
            openCusDevPlanDialog('计划项数据开发',data.data.id);
        }else if(data.event == "info"){/*详情*/
            openCusDevPlanDialog('计划项数据维护',data.data.id);
        }
    });

    function openCusDevPlanDialog(title, id) {
        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['750px', '550px'],
            content: ctx + "/cus_dev_plan/toCusDevPlanPage?id="+id, //iframe的url
            maxmin:true
        });
    }


});
