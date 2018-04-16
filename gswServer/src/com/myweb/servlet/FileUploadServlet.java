package com.myweb.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// final long MAX_SIZE = 2048 * 1024 * 1024;// 设置上传文件最大值为2G，可以改为更大
		final long MAX_SIZE = 1024 * 1024 * 1024;// 设置上传文件最大值为1G，可以改为更大
		// 允许上传的文件格式的列表
		final String[] allowedExt = new String[] { "mp3", "wmv"};
		response.setContentType("text/html");
		// 设置字符编码为UTF-8, 统一编码，处理出现乱码问题
		response.setCharacterEncoding("UTF-8");

		// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();

		// 用以上工厂实例化上传组件
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		// 设置最大上传大小
		sfu.setSizeMax(MAX_SIZE);

		PrintWriter out = response.getWriter();
		// 从request得到所有上传域的列表
		List fileList = null;
		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {// 处理文件尺寸过大异常
			if (e instanceof SizeLimitExceededException) {
				out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");
				out
						.println("<a href=\"common/FileUpload.html\" target=\"_top\">返回</a>");
				return;
			}
			e.printStackTrace();
		}
		// 没有文件上传
		if (fileList == null || fileList.size() == 0) {
			out.println("请选择上传文件<p />");
			out
					.println("<a href=\"common/FileUpload.html\" target=\"_top\">返回</a>");
			return;
		}
		// 文件大小取两位小数
		DecimalFormat digit = new DecimalFormat("0.00");
		// 得到所有上传的文件
		Iterator fileItr = fileList.iterator();
		// 循环处理所有文件
		while (fileItr.hasNext()) {
			FileItem fileItem = null;
			String path = null;
			double size = 0;
			// 得到当前文件
			fileItem = (FileItem) fileItr.next();
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
			if (fileItem == null || fileItem.isFormField()) {
				continue;
			}

			// 得到文件的大小，K为单位并保留两位小数
			size = (double) fileItem.getSize() / 1024;
			if ("".equals(path) || size == 0) {
				out.println("<html><head><title>上传处理界面</title></head>");
				out.println("请选择上传文件<p />");
				out
						.println("<a href=\"common/FileUpload.html\" target=\"_top\">返回</a>");
				out.println("</html>");
				return;
			}
			// 得到文件的完整路径
			path = fileItem.getName();

			// 得到去除路径的文件名
			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			// 得到文件的扩展名(无扩展名时将得到全名)
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
			// 拒绝接受规定文件格式之外的文件类型
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].equals(t_ext))
					break;
			}
			if (allowFlag == allowedExtCount) {
				out.println("<html><head><title>上传处理界面</title></head>");
				out.println("请上传以下类型的文件<p />");
				for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)
					out.println("*." + allowedExt[allowFlag]
							+ "&nbsp;&nbsp;&nbsp;");
				out
						.println("<p /><a href=\"common/FileUpload.html\" target=\"_top\">返回</a>");
				out.println("</html>");
				return;
			}
			try {
				// 保存文件到服务器根目录下
				String serverPath = request.getSession().getServletContext()
						.getRealPath("/");
				String newName = String.valueOf(new Date().getTime()) + "."
						+ t_ext;
				fileItem.write(new File(serverPath + "\\upload\\" + newName));
				System.out.println(newName);
				out.println("<html><head><title>上传处理界面</title></head>");
				// out.println("文件名称为：" + path + "<br>");
				out.println("文件上传成功， 已保存为: " + newName + "<br>" + " 文件大小: "
						+ digit.format(size) + "K <p />");
				out
						.println("<a href=\"javascript:void(0)\" onclick=\"returnVal()\">确定</a>&nbsp;");
				out
						.println("<a href=\"common/FileUpload.html\" target=\"_top\">重新上传</a>");
				out.println("</html>");
				out
						.println("<script>function returnVal(){if(window.opener!=undefined){  window.opener.returnValue = \""
								+ newName
								+ "\"; }else{window.returnValue = \""
								+ newName + "\"; }  window.close();}</script>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}