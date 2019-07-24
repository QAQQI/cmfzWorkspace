<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingOne" >
            <h4 class="panel-title" data-toggle="collapse" data-target="#collapseOne" data-parent="#accordion">
                <a role="button" href="#" aria-expanded="true" aria-controls="collapseOne">
                    <a href="javascript:void(0)" id="head">轮播图</a>
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <a href="javascript:$('#change').load('main/carousel.jsp')" id="emslist">轮播图管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingTwo">
            <h4 class="panel-title" data-toggle="collapse" data-target="#collapseTwo" data-parent="#accordion">
                <a class="collapsed" role="button"  href="javascript:void(0)" aria-expanded="false" aria-controls="collapseTwo">
                    专辑
                </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
            <div class="panel-body">
                <a href="javascript:$('#change').load('main/album.jsp')">专辑和章节管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingThree">
            <h4 class="panel-title" data-toggle="collapse" data-target="#collapseThree" data-parent="#accordion">
                <a class="collapsed" role="button"  href="javascript:void (0)" aria-expanded="false" aria-controls="collapseThree">
                    文章
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <a href="javascript:$('#change').load('main/article.jsp')">文章管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFour">
            <h4 class="panel-title" data-toggle="collapse" data-target="#collapseFour" data-parent="#accordion">
                <a class="collapsed" role="button"  href="javascript:void (0)" aria-expanded="false" aria-controls="collapseFour">
                    用户
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li role="presentation"><a href="javascript:$('#change').load('main/user.jsp')">用户管理</a></li>
                    <li role="presentation"><a href="javascript:$('#change').load('main/mothecharts.jsp')">每月注册用户</a></li>
                    <li role="presentation"><a href="javascript:$('#change').load('main/provinceecharts.jsp')">全国用户分布图</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>