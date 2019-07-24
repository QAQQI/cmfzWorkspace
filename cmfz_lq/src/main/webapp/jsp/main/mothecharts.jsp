<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $(function () {
        $.get("${pageContext.request.contextPath}/user/mothecharts",function () {},"json");
        var goEasy = new GoEasy({
            appkey:'BC-8f3be71a181546e1b56e77b861b5e85a'
        });
        goEasy.subscribe({
            channel:'demo_channel',
            onMessage: function(message){
                console.log(message.content);
                var map = JSON.parse(message.content);
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '每月注册人数'
                    },
                    tooltip: {},
                    legend: {
                        data:['人数']
                    },
                    xAxis: {
                        data: map["month"]
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'bar',
                        data: map["count"]
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    })
</script>