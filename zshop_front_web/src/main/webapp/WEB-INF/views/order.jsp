<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%request.setAttribute("index","2");%>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>确认订单</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script>
        //返回cart.jsp
        function toCart() {
            location.href='${pageContext.request.contextPath}/front/product/toCart';
        }
        
        //生成订单
        function generateOrder() {
            $.post('${pageContext.request.contextPath}/front/product/generateOrder',
                {"customerId":"${sessionScope.customer.id}",
                "price":"${sessionScope.shoppingCart.totalMoney}"},
                    function (result) {
                if(result.status==1){
                    //将订单写入模态框
                    $('#no').html(result.data.no);
                    //显示生成订单模态框
                    $('#buildOrder').modal('show');

                }
                else{
                    layer.msg(result.message,{
                        time:2000,
                        skin:'errorMsg'
                    });
                }
            });
        }
        
    </script>
</head>

<body>
<div class="navbar navbar-default clear-bottom">
    <div class="container">

        <!-- navbar start -->
        <jsp:include page="top.jsp"/>
        <!-- navbar end -->
    </div>
</div>
<!-- content start -->
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="page-header" style="margin-bottom: 0px;">
                <h3>我的购物车</h3>
            </div>
        </div>
    </div>
    <table class="table table-hover table-striped table-bordered">
        <tr>
            <th>序号</th>
            <th>商品名称</th>
            <th>商品图片</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总价</th>
        </tr>
        <c:forEach items="${sessionScope.shoppingCart.items}" var="item" varStatus="s">
        <tr>
            <td>${s.count}</td>
            <td>${item.product.name}</td>
            <td><img src="${pageContext.request.contextPath}/front/product/showPic?image=${item.product.image}" alt="" width="40"></td>
            <td>${item.quantity}</td>
            <td><fmt:formatNumber value="${item.product.price}" type="number" pattern="#.00"/></td>
            <td><fmt:formatNumber value="${item.product.price*item.quantity}" type="number" pattern="#.00"/></td>
        </tr>
        </c:forEach>
        <tr>
            <td colspan="6" class="foot-msg">
                总计：<b> <span><fmt:formatNumber value="${sessionScope.shoppingCart.totalMoney}" type="number" pattern="#.00"/></span></b>元
                <a href="#">
                    <button class="btn btn-warning pull-right " onclick="toCart()">返回</button>
                </a>
                <button class="btn btn-warning pull-right margin-right-15" data-toggle="modal" data-target="#buildOrder" onclick="generateOrder()">生成订单</button>
            </td>
        </tr>
    </table>
</div>
<!-- content end-->
<div class="modal fade" id="buildOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示消息</h4>
            </div>
            <div class="orderMsg">
                <p>
                    订单生成成功！！
                </p>
                <p>
                    订单号：<span id="no"></span>
                </p>
            </div>
        </div>
    </div>
</div>
<!-- footers start -->
<div class="footers">
    版权所有：中兴软件技术
</div>
<!-- footers end -->

</body>

</html>
