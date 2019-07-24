<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="page-header">
    <h1>专辑管理</h1>
</div>
<script>
    $(function () {
        $("#table2").jqGrid({
            styleUI:"Bootstrap",
            rownumbers:true,
            url: "${pageContext.request.contextPath}/album/queryall",  // 远程加载数据的url
            datatype: "json",     // 预期的结果类型
            autowidth: true,    // 自适合宽度
            height: "100%",
            colNames: ["编号", "专辑名称", "专辑封面", "章节数量", "专辑得分","专辑作者","播音员","专辑简介","出版时间"],  // 数组类型，指定每列的列标题名
            colModel: [{  // 注意：colModel数组的长度 必须和 colNames数组的长度一致
                name: "id",   // 指定该列要显示的数据 对应的 json属性名
            }, {
                name: "title", editable: true
            }, {
                name:"cover",editable:true,
                edittype: 'file',formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:100px;;height: 50px' src='${pageContext.request.contextPath}/img/"+cellvalue+"'/>";
                }
            }, {
                name: "count", editable: true
            }, {
                name: "score", editable: true
            },{
                name: "author", editable: true
            },{
                name: "broadcast", editable: true
            },{
                name: "brief", editable: true
            },{
                name: "publishTime", editable: true,edittype:"date"
            }],
            pager: "#page2",
            viewrecords: true,
            rowNum: 4,
            rowList: [2, 4, 6, 8],
            page: 1,
            multiselect: true,
            editurl: "${pageContext.request.contextPath}/album/sduq",
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' ></table><div id='"
                    + pager_id + "'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI:"Bootstrap",
                        viewrecords: true,
                        autowidth: true,
                        url : "${pageContext.request.contextPath}/chapter/queryall?aid="+row_id,
                        datatype : "json",
                        colNames : [ '编号', '专辑编号', '音频名称', '音频大小','音频路径','上传时间','操作'],
                        colModel : [
                            {name : "id",width : 80,key : true,align : "center"},
                            {name : "aid" ,width : 70},
                            {name : "title",width : 70,align : "center", editable: true},
                            {width : 70,align : "center",
                                formatter:function (cellvalue, options, rowObject) {
                                    var bbb=rowObject.size+"KB";
                                    return bbb;
                                }
                            },
                            {name : "downPath",width : 70,align : "center",editable:true,
                                edittype: 'file'},
                            {name : "uploadTime" ,width : 80,align : "center"},
                            {formatter:function (cellvalue, options, rowObject) {
                                    var aa=rowObject.downPath;
                                return "<a href='${pageContext.request.contextPath}/chapter/filedownload?fname="+aa+"' class='btn btn-primary'>下载</a>";
                                },width : 70,align : "right",sortable : false,align : "center"}
                        ],
                        rowNum : 3,
                        pager : pager_id,
                        editurl: "${pageContext.request.contextPath}/chapter/sduq?aid="+row_id,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                    edit:false,del:false,search:false,refresh:false
                    },{},{
                        //添加部分
                        closeAfterAdd:true,
                        afterSubmit:function(response){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/upload",
                                fileElementId:"downPath",
                                data:{"id":response.responseText},
                                type:"post",
                                success:function(){
                                    $("#"+subgrid_table_id).trigger("reloadGrid");
                                }
                            })
                            return "[true]";
                        }
                    });
            }
        }).jqGrid('navGrid', "#page2", {search:false},{
            //修改部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                if(response.responseText==''){
                    $("#table2").trigger("reloadGrid");
                    return "[true]";
                }
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/upload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#table2").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        },{
            //添加部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/upload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#table2").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })
</script>
<table id="table2"></table>
<div id="page2"></div>