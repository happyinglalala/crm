layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        // 设置table的id属性值
        // id属性值
        elem: '#saleChanceList',
        url : ctx+'/sale_chance/list',
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
            {field: 'userName', title: '指派人', align:'center'},
            {field: 'updateDate', title: '修改时间', align:'center'},
            {field: 'state', title: '分配状态', align:'center',templet: function (d){
                // 调用函数返回结果
                return formaterStated(d.state);
                }},
            {field: 'devResult', title: '开发状态', align:'center',templet:function (d){
                return formaterDevResult(d.devResult);
                }},
            {title: '操作', templet:'#saleChanceListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    function formaterStated(state){
        if(state == 0) {
            return "<div style='color: grey'>未分配</div>";
        }else if(state == 1){
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: yellow'>未知</div>未知";
        }
    }

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
                state:$("[name='state']").val()
            },
            page: {
                curr:1
            }
        });
    });


    function deleteSaleChance(data) {
        var checkStatus = table.checkStatus("saleChanceListTable");
        var saleChanceData = checkStatus.data;
        console.log(saleChanceData);
        if(saleChanceData.length<1){
            layer.msg("请选择要删除的记录！！！",{icon:5});
            return;
        }
        layer.confirm("你确定要删除选中的记录吗???",{icon:3,title:"营销机会管理"},function (index){
            layer.close(index);
            var ids = "ids=";
            for(var i = 0;i<saleChanceData.length;i++){
                if(i<saleChanceData.length-1){
                    ids = ids + saleChanceData[i].id +"&ids=";
                }else {
                    ids = ids + saleChanceData[i].id;
                }
            }
            console.log(ids);
            $.ajax({
                type:"put",
                url:ctx+"/sale_chance/delete",
                data:ids,
                success(result){
                    if(result.code==200){
                        layer.msg("删除成功！！");
                        tableIns.reload();
                    }else {
                        layer.msg(result.msg);
                    }
                }
            })
        });
    }

    table.on('toolbar(saleChances)', function(obj){
        if(obj.event == "add"){
            openSaleChanceDialog();
        }else if(obj.event == "del"){
            deleteSaleChance(obj);
        }
    });

    function openSaleChanceDialog(saleChanceId){
        var title = '<h1>营销机会管理 - 添加营销机会</h1>';
        var url = ctx+"/sale_chance/toSaleChancePage";
        if(saleChanceId != null&&saleChanceId != ""){
            title = '<h1>营销机会管理 - 编辑营销机会</h1>';
            url += "?saleChanceId="+saleChanceId;
        }

        //iframe 层
        layui.layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: ['500px', '620px'],
            content: url, //iframe的url
            maxmin:true
        });
    }




    table.on("tool(saleChances)",function (data){
        // console.log(data.data)
        if(data.event == "edit"){//编辑操作
            var saleChanceId = data.data.id;
            openSaleChanceDialog(saleChanceId);
        }else if(data.event == "del"){//删除操作
        //     弹出确认框，询问用户是否删除
            layer.confirm('确认要删除该记录吗？？？',{icon:3,title:"营销机会管理"},function (index){
                layer.close(index);
            //     发送ajax请求，删除记录
                $.ajax({
                    type:"put",
                    url:ctx + "/sale_chance/delete",
                    data:{
                        ids:data.data.id
                    },
                    success:function (result){
                        if(result.code == 200){
                            layer.msg("删除成功！！！",{icon:6});
                            tableIns.reload();
                        }else {
                            layer.msg(result.msg,{icon:5});
                        }
                    }
                })
            });

        }
    });





});
