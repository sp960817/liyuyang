package com.myweb.service;

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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadService  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "";// 返回结果集

		final long MAX_SIZE = 1024 * 1024 * 1024;// 设置上传文件最大值为1G，可以改为更大

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

			result = "error";
		}
		if (result.length() < 1) {

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

				// 得到文件的完整路径
				path = fileItem.getName();

				// 得到去除路径的文件名
				String t_name = path.substring(path.lastIndexOf("\\") + 1);
				// 得到文件的扩展名(无扩展名时将得到全名)
				String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);

				try {
					// 保存文件到服务器根目录下
					String serverPath = request.getSession()
							.getServletContext().getRealPath("/");
					String newName = String.valueOf(new Date().getTime()) + "."
							+ t_ext;
					fileItem
							.write(new File(serverPath + "\\upload\\" + newName));
					
					System.out.println(newName);
					
					result=newName;

				} catch (Exception e) {

					System.out.print(e.getMessage());
					
					result="error";
				}
			}
		}
			/* 返回数据 */
			response.setCharacterEncoding("UTF-8");

			response.setHeader("content-type", "text/html;charset=UTF-8");

			System.out.println("返回报文:" + result);

			PrintWriter pw = response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
