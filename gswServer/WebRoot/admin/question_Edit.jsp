<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
		<link rel="stylesheet" type="text/css" href="css/base.css" />

		<title>Insert title here</title>
	</head>
	<body leftmargin="8" topmargin='8'>
		<form id="form" action="<%=path%>/QuestServlet?action=editSave"
			method="post">
			<input type="hidden" name="id" id="id"
				value='<c:out value="${question.id}"></c:out>' />
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 8px">
				<tr>
					<td>
						<div style='float: left'>
							<img height="16" src="<%=path%>/images/add.png" width="16" />
							&nbsp;
							<strong>意见反馈</strong>
						</div>
					</td>
				</tr>
				 
			</table>
			<table width="98%" align="center" border="0" cellpadding="4"
				cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom: 8px">
				 
				<tr bgcolor="#FFFFFF">
					<td width="10%" align="center">
						标题:
					</td>
					<td width="90%">
						<c:out value="${question.title}" />
					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="10%" align="center">
					内容:
					</td>
					<td width="90%" style="height: 100px">
						<c:out value="${question.content}" />
					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="10%" align="center">
						反馈内容:
					</td>
					<td width="90%" style="height: 160px">
						<textarea Rows="10" cols="100" id="replaycontent"name="replaycontent"><c:out value="${question.replaycontent}" /></textarea>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td align="center">
					</td>
					<td colspan="3">
						&nbsp;&nbsp;
						<br />
						<input type="submit" value="保存" />
						&nbsp;
						<a href="QuestServlet?action=list" target="right">返回列表</a>
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
<script type="text/javascript">
	window.onload = function() {

		var alertNote = "${alertNote}";
		if (alertNote == "1") {
			alert("操作成功!");

		} else if (alertNote == "0") {
			alert("操作失败，请联系管理员!");
		}
	}
</script>
