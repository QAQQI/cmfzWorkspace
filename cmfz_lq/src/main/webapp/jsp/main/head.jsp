<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(function () {
        $("#addUser").modal({
            backdrop: false,
            keyboard: false,
            show: false
        });
    })
    function aa(){
        var serialize = $("#form1").serialize();
        $.post("${pageContext.request.contextPath}/admin/regist",serialize, function (map) {
            var arr = map["code"];
            if(arr==300||arr==400){
                document.getElementById("sp").innerText=map["message"];
                return;
            }
            location.href="${pageContext.request.contextPath}/jsp/main.jsp";
        },"json");
        <%--$("#change").load("${pageContext.request.contextPath}/main/right.jsp");--%>
    }
</script>

<nav class="navbar navbar-default">
    <div class="container-fluid navbar-inverse">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">持明法州后台管理系统v1.0</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <shiro:guest>
                    <li class="dropdown">
                        <a href="javascript:void(0)" data-toggle="modal" data-target="#addUser">请先登陆&nbsp;<span class="glyphicon glyphicon-user" aria-hidden="true"></span></a>
                    </li>
                </shiro:guest>
                <shiro:user>
                    <li><p class="navbar-text">欢迎：<shiro:principal></shiro:principal></p></li>
                    <li class="dropdown">
                        <a href="${pageContext.request.contextPath}/admin/exit">退出登陆&nbsp;<span class="glyphicon glyphicon-log-in"
                                                                                                aria-hidden="true"></span></a>
                    </li>
                </shiro:user>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="modal fade" tabindex="-1" id="addUser">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">登陆</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form1">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账户姓名</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="text" name="password" class="form-control"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span id="sp" style="color: red"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="aa()">登陆</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>