<%@ page language="java" import="com.mbft.smbms.pojo.User" pageEncoding="UTF-8"%>
<%@ page import="com.mbft.smbms.util.Constants01" %>

<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <img class="wColck" src="${pageContext.request.contextPath }/images/clock.jpg" alt=""/>
    <div class="wFont">
        <%
            User user = (User)request.getSession().getAttribute(Constants01.USER_SERSSION);
            String username = "";
            if (user!=null){
                username += user.getUserName();
            }
        %>
        <h2><%=username%></h2>
        <p>欢迎来到超市订单管理系统!</p>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
