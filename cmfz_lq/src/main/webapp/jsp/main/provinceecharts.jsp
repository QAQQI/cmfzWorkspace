<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="main1" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $(function () {
        $.get("${pageContext.request.contextPath}/user/provinceecharts", function () {
        }, "json");
        var goEasy = new GoEasy({
            appkey: 'BC-8f3be71a181546e1b56e77b861b5e85a'
        });
        goEasy.subscribe({
            channel: 'demo_channel0',
            onMessage: function (message) {
                console.log(message.content);
                var map = JSON.parse(message.content);
                console.log(map["w"])
                console.log(map["m"]);
                var myChart = echarts.init(document.getElementById('main1'));
                var option = {
                    title: {
                        text: '全国各个省份注册人数',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['男', '女']
                    },
                    visualMap: {
                        min: 0,
                        max: 2500,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: map["m"]
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: map["w"]
                        }
                    ]
                };
                myChart.setOption(option);
            }
        });
    });


    // 基于准备好的dom，初始化echarts实例
</script>