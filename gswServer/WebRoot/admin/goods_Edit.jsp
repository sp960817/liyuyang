<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<base target="_self">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
<script type="text/javascript">
	function advil() {
		var goodsname = document.getElementById("goodsname").value;	 
		if (goodsname == "" || goodsname == null) {
			alert("训练不能为空!");
			return false;
		}
		var TYPEID = document.getElementById("TYPEID").value;	 
		if (TYPEID == "" || TYPEID == null) {
			alert("训练类型不能为空!");
			return false;
		}
		
		var imgpath = document.getElementById("imgpath").value;	 
		if (imgpath == "" || imgpath == null) {
			alert("训练图片不能为空!");
			return false;
		}
		
		var filepath = document.getElementById("filepath").value;	 
		if (filepath == "" || filepath == null) {
			alert("音频文件不能为空!");
			return false;
		}
		 
		this.form1.submit();
	}
	</script>
	</head>
	<body leftmargin="8" topmargin='8' onload="noteAlert()">
		<form id="form" action="<%=path%>/GoodsServlet?action=editSave"
			method="post">
			<input type="hidden" id="id" name="id" value="${goods.id}"/>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 8px">
				<tr>
					<td>
						<div style='float: left'>
							<img height="16" src="<%=path%>/images/add.png" width="16" />
							&nbsp;
							<strong>编辑训练</strong>
						</div>
					</td>
				</tr>
				<tr>
					<td height="1" background="<%=path%>/images/sp_bg.gif"
						style='padding: 0px'>
					</td>
				</tr>
			</table>
			<table width="98%" align="center" border="0" cellpadding="4"
				cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom: 8px">

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						训练名称:
					</td>
					<td width="38%">
						<input type="text" ID="goodsname" name="goodsname" Height="21px" 	value='${goods.name}'
							Width="196px" />

					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						训练分类:
					</td>
					<td width="38%">
						<select id="goodstypeid" name="goodstypeid">
							<option value="">
								--请选择--
							</option>
							<c:forEach items="${goodsTypeList}" var="goodsType">

								<c:choose>

									<c:when test="${goodsType.ID==goods.typeid}">
										<option value="${goodsType.ID}" selected="selected">
											${goodsType.TYPENAME}
										</option>
									</c:when>

									<c:otherwise>
										<option value="${goodsType.ID}">
											${goodsType.TYPENAME}
										</option>
									</c:otherwise>

								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						训练图片:
					</td>
					<td width="38%">
						<input type="text" ID="imgpath" name="imgpath" Height="21px" 	value='${goods.imgpath}'
							readonly="readonly" style="width: 200px" />
						<input type="button" value="选择图片" onclick=
	openDLG();
/>

					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						音频文件:
					</td>
					<td width="38%">
						<input type="text" ID="filepath" name="filepath" Height="21px" value='${goods.filepath}'
							readonly="readonly" style="width: 200px" />
						<input type="button" value="选择音频" onclick=
	openDLG1();
/>
						(只能添加标准的mp3,wmv格式)

					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						训练简介:
					</td>
					<td width="38%">
						<textarea rows="5" cols="100" ID="description" name="description"  >${goods.description}</textarea>
					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td align="center">
					</td>
					<td colspan="3">
						&nbsp;&nbsp;
						<br />
						<input type="submit" value="保存"   onclick=" return advil()"/>	&nbsp;
						<a href="<%=path%>/GoodsServlet?action=list" target="right">返回列表</a>

					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>
	//获得标识并显示：             
	function noteAlert() {
		var alertNote = "${alertNote}";
		if (alertNote == "1") {
			alert("操作成功!");

		} else if (alertNote == "0") {
			alert("添加失败，请联系管理员!");
		}
	}

	function openDLG() {
		t = window
				.showModalDialog('common/imageUpload.jsp', '',
						'dialogHeight:100px; dialogWidth:400px;dialogTop:250px;dialogLeft:500px;')

		//for Chrome  
		if (t == undefined) {
			t = window.returnValue;
		}
		if (t != null) {
			document.getElementById("imgpath").value = t;
		}
	}

	function openDLG1() {
		t = window
				.showModalDialog('common/FileUpload.html', '',
						'dialogHeight:100px; dialogWidth:400px;dialogTop:250px;dialogLeft:500px;')

		//for Chrome  
		if (t == undefined) {
			t = window.returnValue;
		}
		if (t != null) {
			document.getElementById("filepath").value = t;
		}
	}
</script>
