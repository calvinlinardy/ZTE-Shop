<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>我的购物车</title>/
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css"/>
    <script>
        function shopping() {
            location.href='${pageContext.request.contextPath}/front/product/findByParams';
        }

        //显示删除模态框
        function  showDelModal(id,count) {
            $('#deleteItemId').val(id);
            $('#count').val(count);
            $('#delItemModal').modal('show');
        }

        //删除商品
        function  deleteItem() {
            $.post('${pageContext.request.contextPath}/front/product/removeItemsByIds',
                {"ids":$('#deleteItemId').val()},
                function (result) {
                    if(result.status==1){
                        layer.msg('删除成功',{
                            time:2000,
                            skin:'successMsg'
                        },function () {
                            //刷新整个页面
                            //location.href='${pageContext.request.contextPath}/front/product/toCart';
                            var count=$('#count').val();
                            //console.log(count);
                            //找到对应的行，将其删除
                            $('#id_'+count).remove();
                            //重新设置总价
                            $('#totalMoney').html(result.data);
                        });

                    }
                    else{
                        layer.msg(result.message,{
                            time:2000,
                            skin:'errorMsg'
                        },function () {
                            //刷新整个页面
                            //location.href='${pageContext.request.contextPath}/front/product/toCart';
                            var count=$('#count').val();
                            //console.log(count);
                            //找到对应的行，将其删除
                            $('#id_'+count).remove();
                            //总价清零
                            $('#totalMoney').html(0);
                        });
                    }
                });
        }

        $(function () {
            //修改数量
            //文本框内容发生改变时触发
            $(':text').change(function () {
                //alert(1);
                //获取文本框原来的值
                var quantityVal=$.trim(this.value);
                var reg=/^\d+$/g;
                var quantity=-1;
                var flag=false;
                if(reg.test(quantityVal)){
                    //获取该数量词
                    quantity=parseInt(quantityVal);
                    if(quantity>0){
                        flag=true;
                    }

                }
                //console.log(flag);
                //如果输入的数字<=0,将原值赋予文本框
                if(!flag){
                    alert('输入的商品数量必须大于等于0');
                    //将该值之前最近的一次正确值写回文本框
                    $(this).val($(this).attr('class'));
                    return;
                }
                //弹出确认对话框
                //单击取消按钮
                var $tr=$(this).parent().parent();
                var title=$tr.find('td:eq(2)').text();
                if(!confirm('确定要修改【'+title+'】的数量吗？')){
                    //将该值之前最近的一次正确值写回文本框
                    $(this).val($(this).attr('class'));
                    return;
                }


                //单击确定按钮
                //将class属性的值进行更新，从而获取最新的有效值
                else{
                    $(this).attr('class',$(this).val());
                }

                //发送ajax请求，更新商品总价和总计
                //1.请求地址:controller
                var url='${pageContext.request.contextPath}/front/product/updateItemQuantity';
                //2.请求参数：JSON:method:updateItemQuantity,id:id值,quantity:quantity
                var idVal=$.trim(this.name);
                var args={"id":idVal,"quantity":quantityVal,"time":new Date()}
                //3.在updateItemQuantity方法中，获取quantity,id,再获取购物车对象
                //4.传回json数据：itemMoney:XX,totalManey:XX
                //5.更新页面的itemMoney,totalmoney
                var itemCount=$(this).parent().parent().find('td').eq(1).html();
                $.post(url,args,function (data) {
                    //console.log(data.itemMoney+','+data.totalMoney);
                    var itemMoney=data.itemMoney;
                    var totalMoney = data.totalMoney;
                    //将这2个价格写回页面
                    $('#itemMoney_'+itemCount).html(itemMoney);
                    $('#totalMoney').html(totalMoney);

                });
            });

            //全选全不选
            //该函数必须在初始化函数内部，因为在页面加载完成后，需要绑定该元素的onclick事件，这样便于人们通过该事件去访问
            $('#all').click(function () {
                //alert(1);
                $('tr[id] input').prop('checked',$('#all').prop('checked'));
            });

            //批量删除
            $('#del').bind('click',function () {
                //alert(1);
                //被选中的复选框的长度
                //console.log($('table.table tr[id] input:checked').length);
                if($('table.table tr[id] input:checked').length==0){
                    layer.msg('请选择需要删除的商品',{
                        time:2000,
                        skin:'successMsg'
                    });
                }
                else{
                    //组装所有被选中的id--->ids数组
                    var ids=0;
                    $('tr[id] input:checked').each(function () {
                        //console.log(this.id);
                        ids+=this.id+','
                    });
                    //console.log(ids);
                    //得到id的连接字符串，如：1,2,3,4,
                    ids=ids.substr(1,ids.length-2);
                    //console.log(ids);
                    $.post('${pageContext.request.contextPath}/front/product/removeItemsByIds',
                        {"ids":ids},
                        function (result) {
                            if(result.status==1){
                                layer.msg('删除成功',
                                    {time:2000,
                                    skin:'successMsg'},
                                function () {
                                    //将对应选中行删除
                                    $('table.table tr[id] input:checked').parent().parent().remove();
                                    //重新计算总价
                                    $('#totalMoney').html(result.data);

                                });
                            }
                            else{
                                layer.msg(result.message,
                                    {time:2000,
                                    skin:'errorMsg'},
                                function () {
                                    //将对应选中行删除
                                    $('table.table tr[id] input:checked').parent().parent().remove();
                                    //重新计算总价
                                    $('#totalMoney').html(0);
                                });
                            }
                        });
                }






            });
        });
        
        //清空购物车
        //注意：函数名不能使用clear,clear是js中的默认函数,是window对象下的一个函数
        //该函数写在初始化函数外面，因为该函数是通过触发事件去调用的。
        function clearShoppingCart() {
            //alert(1);
            $.post('${pageContext.request.contextPath}/front/product/clear',function (result) {
                if(result.status==1){
                    layer.msg(result.message,
                        {time:2000,
                        skin:'successMsg'},function () {
                            //刷新整个页面,重新返回cart.jsp
                            location.href='${pageContext.request.contextPath}/front/product/toCart';
                        });
                }
            });
        }
        
        //显示订单页面
        function  showOrder() {
            location.href='${pageContext.request.contextPath}/front/product/showOrder';
        }



    </script>
</head>

<body>
<%request.setAttribute("index","2");%>
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
            <th>
                <input type="checkbox" id="all">
            </th>
            <th>序号</th>
            <th>商品名称</th>
            <th>商品图片</th>
            <th>商品数量</th>
            <th>单价</th>
            <th>商品总价</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${shoppingCart.items}" var="item" varStatus="s">
        <tr id="id_${s.count}">
            <td>
                <input type="checkbox" id="${item.product.id}">
            </td>
            <td>${s.count}</td>
            <td>${item.product.name}</td>
            <td> <img src="${pageContext.request.contextPath}/front/product/showPic?image=${item.product.image}" alt="" width="40"></td>
            <td>
                <input type="text" value="${item.quantity}" size="5" class="${item.quantity}" name="${item.product.id}">
            </td>
            <td><fmt:formatNumber value="${item.product.price}" type="number" pattern="#.00"/></td>
            <td id="itemMoney_${s.count}"><fmt:formatNumber value="${item.product.price*item.quantity}" type="number" pattern="#.00"/></td>
            <td>
                <button class="btn btn-success" type="button"> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>修改</button>
                <button class="btn btn-danger" type="button" onclick="showDelModal(${item.product.id},${s.count})">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                </button>
            </td>
        </tr>
        </c:forEach>

        <tr>
            <td colspan="8" align="right">
                <button class="btn btn-warning margin-right-15" type="button" id="del"> 删除选中项</button>
                <button class="btn btn-warning  margin-right-15" type="button" onclick="clearShoppingCart()"> 清空购物车</button>
                <button class="btn btn-warning margin-right-15" type="button" onclick="shopping()"> 继续购物</button>
                <a href="#">
                    <button class="btn btn-warning " type="button" onclick="showOrder()"> 结算</button>
                </a>
            </td>
        </tr>
        <tr>
            <td colspan="8" align="right" class="foot-msg">
                总计： <b><span id="totalMoney"><fmt:formatNumber value="${shoppingCart.totalMoney}" type="number" pattern="#.00"/></span> </b>元
            </td>
        </tr>
    </table>
</div>
<!-- content end-->
<!-- footers start -->
<div class="footers">
    版权所有：中兴软件技术
</div>
<!-- footers end -->
<!-- 删除确认提示框 start -->
<div class="modal fade" tabindex="-1" id="delItemModal">
    <input type="hidden" id="deleteItemId">
    <input type="hidden" id="count">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-sm">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">提示消息</h4>
            </div>
            <input type="hidden" id="productTypeId"/>
            <div class="modal-body">
                <h4>确认要删除该商品吗？</h4>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary delProductType" data-dismiss="modal" onclick="deleteItem()">确定</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 删除确认提示框 end -->

</body>

</html>