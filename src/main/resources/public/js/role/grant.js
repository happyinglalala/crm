$(function () {
    loadModuleInfo();
});


var zTreeObj;
function loadModuleInfo() {
    $.ajax({
        type:"post",
        // 查询所有资源列表时，传递角色ID,查询当前角色授权的资源
        url:ctx+"/model/queryAllModules?roleId="+$('[name="roleId"]').val(),
        dataType:"json",
        success:function (data) {
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback:{
                    onCheck:zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
        }
    });
}

function zTreeOnCheck(event,treeId,treeNode){
    // alert(treeNode.tId + "," + treeNode.name + "," + treeNode.checked);
    var nodes = zTreeObj.getCheckedNodes(true);
    if(nodes.length > 0){
        var mIds = "mIds=";
        //     遍历节点集合获取资源ID
        for(var i = 0;i<nodes.length;i++){
            if(i==nodes.length-1){
                mIds += nodes[i].id;
            }else {
                mIds += nodes[i].id + "&mIds=";
            }
        }
    }
    // console.log(mIds);
    var roleId = $('[name="roleId"]').val();
    $.ajax({
        type:"post",
        url:ctx + "/role/addGrant",
        data:mIds+"&roleId="+roleId,
        dataType:"json",
        success:function (data){
            console.log(data);
        }
    });
};


/*
function zTreeOnCheck(event, treeId, treeNode) {
    //alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
    var nodes= zTreeObj.getCheckedNodes(true);
    var mids="mids=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mids=";
        }else{
            mids=mids+nodes[i].id;
        }
    }

    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+$("input[name='roleId']").val(),
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })

}*/
