<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/templates/taglibs.jsp"%>
<%@ include file="/templates/include.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>报销单详情</title>
		<script type="text/javascript">		 
			$(function(){
				$('#div_createorupdate').tabs({  
				    border:false 
				});
			});
		</script>
	</head>
	<body id="_right_panel_body">
		<div id="wrapper" >
			<div id="_title" class="content_title">
                <div id="_title_label" class="title_label">
    				<span>报销单详情</span>								
                </div>
            </div>
			<div id="div_createorupdate">
				<div title="报销单详情">
					<div style="margin:10px 0px 0px 10px">
						<span class="LABEL_NORMAL">金额：</span>
						<input id="payment.amount" readonly="readonly" name="payment.amount" type="text" style="width: 150px" value="${payment.amount}" ></input>
					</div>
					<div style="margin:10px 0px 0px 10px">
						<span class="LABEL_NORMAL">描述：</span>
						<input id="payment.description" readonly="readonly" name="payment.description" type="text" style="width: 150px" value="${payment.description}" ></input>
					</div>
				</div>
				<div title="报销单历史">
					<table width="90%" class="smftable" style="margin:20px">
		                <thead>
		                    <tr>
		                    	<th width="40px">编号</th>
		                        <th width="40px">流程名称</th>
		                        <th width="80px">任务名称</th>
		                        <th width="100px">操作人</th>
		                        <th width="80px">操作时间</th>
		                        <th width="120px">备注</th>
		                    </tr>
		                </thead>
		                <tbody>
		                    <c:forEach items="${listComments}" var="comment" varStatus="num">
		                        <tr>
		                            <td>${num.index + 1}</td>
		                            <td><span>${comment.processDefinitionName}</span></td>
		                            <td><span>${comment.taskName}</span></td>
		                            <td><span>${comment.userId}</span></td>
		                            <td><span><fmt:formatDate value="${comment.time}"  type="both" /></span></td>
		                            <td><span>${comment.fullMessage}</span></td>
		                        </tr>
		                    </c:forEach>
		                </tbody>
		            </table>
				</div>
			</div>
		</div>
	</body>
</html>
