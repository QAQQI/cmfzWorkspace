<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="page-header">
    <h1>用户管理</h1>
</div>
<script>
    function userregist(){
        var formData = new FormData($('#form5')[0]);
        $.ajax({
            type: 'post',
            url: "${pageContext.request.contextPath}/user/regist",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (result) {
                console.log(result);
            }
        });
        $("#table3").trigger("reloadGrid");
        $("#regist").modal("hide");
    }

    function userlogin(){
        var formData = new FormData($('#form4')[0]);
        $.ajax({
            type: 'post',
            url: "${pageContext.request.contextPath}/user/login",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (result) {
                console.log(result);
            }
        });
        $("#login").modal("hide");
    }
    function uploadxls() {
        var formData = new FormData($('#form3')[0]);
        $.ajax({
            type: 'post',
            url: "${pageContext.request.contextPath}/user/uploadxls",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function () {
            }
        });
        $("#table3").trigger("reloadGrid");
        $("#selectarea").modal("hide");
    }

    $(function () {
        $("#selectarea").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
        $("#login").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
        $("#regist").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
        $("#table3").jqGrid({
            styleUI: "Bootstrap",
            rownumbers: true,
            url: "${pageContext.request.contextPath}/user/queryall",  // 远程加载数据的url
            datatype: "json",     // 预期的结果类型
            autowidth: true,    // 自适合宽度
            height: "100%",
            colNames: ["编号", "电话", "密码", "盐", "法名", "省份", "城市", "性别", "个性签名", "头像", "状态", "注册时间"],  // 数组类型，指定每列的列标题名
            colModel: [{  // 注意：colModel数组的长度 必须和 colNames数组的长度一致
                name: "id",   // 指定该列要显示的数据 对应的 json属性名
            }, {
                name: "phone", editable: true
            }, {
                name: "password", editable: true
            }, {
                name: "salt"
            }, {
                name: "dharmaName", editable: true
            }, {
                name: "province", editable: true
            }, {
                name: "city", editable: true
            }, {
                name: "gender", editable: true, edittype: "select", editoptions: {value: "m:男;w:女"}
            }, {
                name: "personalSign", editable: true
            }, {
                name: "profile", editable: true,
                edittype: 'file', formatter: function (cellvalue, options, rowObject) {
                    return "<img style='width:100px;;height: 50px' src='${pageContext.request.contextPath}/img/" + cellvalue + "'/>";
                }
            }, {
                name: "status", editable: true, edittype: "select", editoptions: {value: "on:正常;off:冻结"}
            }, {
                name: "registTime", edittype: "date", width: 200,editable: true
            }],
            pager: "#page3",
            viewrecords: true,
            rowNum: 4,
            rowList: [2, 4, 6, 8],
            page: 1,
            multiselect: true,
            editurl: "${pageContext.request.contextPath}/user/sduq"
        }).jqGrid('navGrid', "#page3", {search: false}, {
            //修改部分
            closeAfterEdit: true,
            afterSubmit: function (response) {
                if (response.responseText == '') {
                    $("#table3").trigger("reloadGrid");
                    return "[true]";
                }
                $.ajaxFileUpload({
                    url: "${pageContext.request.contextPath}/user/upload",
                    fileElementId: "profile",
                    data: {"id": response.responseText},
                    type: "post",
                    success: function () {
                        $("#table3").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        }, {
            //添加部分
            closeAfterAdd: true,
            afterSubmit: function (response) {
                $.ajaxFileUpload({
                    url: "${pageContext.request.contextPath}/user/upload",
                    fileElementId: "profile",
                    data: {"id": response.responseText},
                    type: "post",
                    success: function () {
                        $("#table3").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })
</script>
<a href="${pageContext.request.contextPath}/user/uploadex" class="btn btn-primary">导出</a>
<a href="javascript:void(0)" data-toggle="modal" data-target="#selectarea" class="btn btn-primary">导入</a>
<a href="javascript:void(0)" data-toggle="modal" data-target="#login" class="btn btn-primary">用户登陆</a>
<a href="javascript:void(0)" data-toggle="modal" data-target="#regist" class="btn btn-primary">用户注册</a>
<table id="table3"></table>
<div id="page3"></div>
<div class="modal fade" id="selectarea">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">导入</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form3" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-sm-4">请选择xml文件</label>
                        <div class="col-sm-8">
                            <input type="file" name="file"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="uploadxls()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<div class="modal fade" id="login">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">用户登陆</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form4">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名(手机号)</label>
                        <div class="col-sm-8">
                            <input type="text" name="phone" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" name="password" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机验证码</label>
                        <div class="col-sm-8">
                            <input type="text" name="code" class="form-control"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span id="sp1" style="color: red"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="userlogin()">登陆</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<div class="modal fade" id="regist">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">用户注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form5" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名(手机号)</label>
                        <div class="col-sm-8">
                            <input type="text" name="phone" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" name="password" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">上师名称</label>
                        <div class="col-sm-8">
                            <input type="text" name="dharmaName" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">省份</label>
                        <div class="col-sm-8">
                            <input type="text" name="province" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">城市</label>
                        <div class="col-sm-8">
                            <input type="text" name="city" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">性别</label>
                        <div class="radio" class="col-sm-8">
                            &nbsp;&nbsp;
                            <label>
                                <input type="radio" name="gender" value="m" checked>
                                男
                            </label>
                            <label>
                                <input type="radio" name="gender" value="w">
                                女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">个性签名</label>
                        <div class="col-sm-8">
                            <input type="text" name="personalSign" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">头像路径</label>
                        <div class="col-sm-8">
                            <input type="file" name="file"/>
                            <br>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <span id="sp2" style="color: red"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="userregist()">注册</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>