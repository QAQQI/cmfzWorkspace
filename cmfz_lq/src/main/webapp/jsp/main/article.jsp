<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="page-header">
    <h1>文章管理</h1>
</div>
<script>
    function cc(a) {
        var a=a.getAttribute("ooo");
        document.getElementById("content1").innerHTML=a;
    }
    function bb() {
        $("#query").modal("hide");
    }
    function aa(){
        var serialize = $("#form2").serialize();
        $.post("${pageContext.request.contextPath}/article/sduq",serialize, function (result) {
        },"json");
        $("#table4").trigger("reloadGrid");
        $("#addArticle").modal("hide");
    }

    $(function () {
        $("#addArticle").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
        $("#query").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
        $("#table4").jqGrid({
            styleUI:"Bootstrap",
            rownumbers:true,
            url: "${pageContext.request.contextPath}/article/queryall",  // 远程加载数据的url
            datatype: "json",     // 预期的结果类型
            autowidth: true,    // 自适合宽度
            height: "100%",
            colNames: ["编号", "上师编号", "名称", "内容", "创建时间"],  // 数组类型，指定每列的列标题名
            colModel: [{  // 注意：colModel数组的长度 必须和 colNames数组的长度一致
                name: "id"  // 指定该列要显示的数据 对应的 json属性名
            }, {
                name: "gid"
            }, {
                name:"title"
            }, {
                name:"content",
                formatter:function (cellvalue, options, rowObject) {  // 自定义单元格内容
                    return "<a href='javascript:void(0)' data-toggle='modal' data-target='#query' onclick='cc(this)' ooo='"+cellvalue+"'>查看内容</a>"

                }
            }, {
                name: "publishTime"
            }],
            pager: "#page4",
            viewrecords: true,
            rowNum: 4,
            rowList: [2, 4, 6, 8],
            page: 1,
            multiselect: true,
            editurl: "${pageContext.request.contextPath}/article/sduq"
        }).jqGrid('navGrid', "#page4", {add:false,edit:false,search:false},{},{});
            KindEditor.create('#content',{
                width : '800px',
                uploadJson:'${pageContext.request.contextPath}/article/upload',
                fileManagerJson:'${pageContext.request.contextPath}/article/showAll',
                allowFileManager:true,
                filePostName:'file',
                afterBlur:function(){
                    this.sync();
                }
            });
    })
</script>
<a href="javascript:void(0)" data-toggle="modal" data-target="#addArticle" class="btn btn-primary">添加文章</a>
<table id="table4"></table>
<div id="page4"></div>
<div class="modal fade" id="addArticle">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">登陆</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form2">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">上师编号</label>
                        <div class="col-sm-10">
                            <input type="text" name="gid" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control"/>
                        </div>
                    </div>
                    <div class="text-center">
                        <label>内容</label>
                    </div>
                    <div class="form-group">
                        <div>
                            <input type="hidden" name="oper" value="add">
                            <textarea id="content" name="content" class="form-control"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span id="sp" style="color: red"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="aa()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<div class="modal fade" id="query">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">内容</h4>
            </div>
            <div class="modal-body">
                <span id="content1"></span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="bb()">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>