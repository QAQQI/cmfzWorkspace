<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:hasPermission name="user:updatePassword">
    <script type="text/javascript">
        alert("22");
        function edit(){
            $('.edit').css('display','');//display不用block原因是避免改变行间样式
        }
    </script>
</shiro:hasPermission>

<shiro:hasPermission name="carousel:add">
    <script type="text/javascript">
        function add(){
            $('.add').css('display','');
        }
    </script>
</shiro:hasPermission>

<script>
    $(function () {
        $("#table1").jqGrid({
            styleUI:"Bootstrap",
            rownumbers:true,
            url: "${pageContext.request.contextPath}/carousel/queryall",  // 远程加载数据的url
            datatype: "json",     // 预期的结果类型
            autowidth: true,    // 自适合宽度
            height: "100%",
            colNames: ["编号", "轮播图名称", "轮播图图片", "状态", "创建时间"],  // 数组类型，指定每列的列标题名
            colModel: [{  // 注意：colModel数组的长度 必须和 colNames数组的长度一致
                name: "id",   // 指定该列要显示的数据 对应的 json属性名
            }, {
                name: "title", editable: true
            }, {
                name:"imgPath",editable:true,
                edittype: 'file',formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:100px;;height: 50px' src='${pageContext.request.contextPath}/img/"+cellvalue+"'/>";
                }
            }, {
                name: "status", editable: true,edittype : "select",editoptions : {value : "on:on;off:off"}
        }, {
                name: "createTime", editable: true,edittype:"date"
            }],
            pager: "#page",
            // loadComplete: function() {//重点，loadComplete方法为在表格加载完毕之后执行
            //     if(typeof edit!= 'undefined' && edit instanceof Function){//验证方法是否存在，如果shiro没有权限这个方法就不会存在，
            //         //如果用户有编辑权限，就会执行此edit方法，展示编辑按钮
            //         edit();
            //     }
            //     if(typeof add!= 'undefined' && add instanceof Function){
            //         add();
            //     }
            // },
            viewrecords: true,
            rowNum: 4,
            rowList: [2, 4, 6, 8],
            page: 1,
            multiselect: true,
            editurl: "${pageContext.request.contextPath}/carousel/sduq"
        }).jqGrid('navGrid', "#page", {search:false},{
            //修改部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                if(response.responseText==''){
                    $("#table1").trigger("reloadGrid");
                    return "[true]";
                }
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/carousel/upload",
                    fileElementId:"imgPath",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#table1").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        },{
            //添加部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/carousel/upload",
                    fileElementId:"imgPath",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#table1").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })
</script>
<div class="page-header">
    <h1>轮播图管理</h1>
</div>
<table id="table1"></table>
<div id="page"></div>
