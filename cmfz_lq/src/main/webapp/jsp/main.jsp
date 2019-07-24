<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../echarts/echarts.js"></script>
    <script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script src="https://www.echartsjs.com/gallery/vendors/echarts/map/js/china.js?_v_=1553896255267"></script>
    <title>持明法洲后台管理系统</title>
</head>
<body>
<jsp:include page="main/head.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <jsp:include page="main/left.jsp"></jsp:include>
        </div>
        <div class="col-xs-10" id="change">
            <jsp:include page="main/right1.jsp"></jsp:include>
        </div>
    </div>
</div>
<jsp:include page="main/down.jsp"></jsp:include>
</body>
</html>