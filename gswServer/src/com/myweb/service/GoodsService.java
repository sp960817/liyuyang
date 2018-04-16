package com.myweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myweb.dao.GoodsDao;
import com.myweb.dao.GoodsTypeDao;
import com.myweb.domain.Goods;
import com.myweb.domain.Note;

public class GoodsService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		action = _request.getParameter("action").toString();

		if (action.equals("add")) {

			addGoods();

		} else if (action.equals("mylist")) {

			listMyGoods();

		} else if (action.equals("list")) {

			listGoods();
		} else if (action.equals("search")) {

			searchGoods();
		} else if (action.equals("view")) {

			viewGoods();
		} else if (action.equals("editsave")) {

			editsaveGoods();
		} else if (action.equals("delete")) {

			deleteGoods();
		}

	}

	public void listMyGoods() throws ServletException, IOException {

		String result = "";// 返回结果集

		String userid = _request.getParameter("userid").toString();

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getMyList(userid);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void editsaveGoods() throws ServletException, IOException {

		String goods = _request.getParameter("goods").toString();

		goods = new String(goods.getBytes("ISO8859-1"), "UTF-8");

		String result = "";// 返回结果集

		try {

			Goods goodsObj = JSONObject.parseObject(goods, Goods.class);

			GoodsDao goodsDAO = new GoodsDao();

			goodsDAO.updateGoods(goodsObj);

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void deleteGoods() throws ServletException, IOException {

		String result = "";// 返回结果集

		String goodsid = _request.getParameter("goodsid").toString();

		GoodsDao goodsDAO = new GoodsDao();

		try {

			goodsDAO.delGoodsById(goodsid);

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void addGoods() throws ServletException, IOException {

		String goods = _request.getParameter("goods").toString();

		goods = new String(goods.getBytes("ISO8859-1"), "UTF-8");

		String result = "";// 返回结果集

		try {

			Goods goodsObj = JSONObject.parseObject(goods, Goods.class);

			GoodsDao goodsDAO = new GoodsDao();

			goodsDAO.addGoods(goodsObj);

			result = "ok";

		} catch (Exception e) {

			System.out.print(e.getMessage());

			result = "error";

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	// 查看
	public void viewGoods() throws ServletException, IOException {

		String result = "";// 返回结果集

		String goodsId = _request.getParameter("goodsid").toString();

		try {

			GoodsDao goodsDAO = new GoodsDao();

			Goods goods = new Goods();

			goods = goodsDAO.getGoodsById(goodsId);

			if (goods != null) {

				result = JSONObject.toJSONString(goods);
			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void searchGoods() throws ServletException,
			IOException {

		String result = "";// 返回结果集
		
		String name = _request.getParameter("name").toString();

		name = new String(name.getBytes("ISO8859-1"), "UTF-8");

		String type = _request.getParameter("type").toString();

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsByNameAndType(name,type);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void searchlistGoods(String goodstype) throws ServletException,
			IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsListByType(goodstype);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void myListGoods(String userid) throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getMyList(userid);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void listGoods() throws ServletException, IOException {

		String result = "";// 返回结果集

		String typeid = _request.getParameter("typeid").toString();

		try {

			GoodsDao goodsDao = new GoodsDao();

			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();

			goodsList = goodsDao.getGoodsListByType(typeid);

			if (goodsList != null) {

				String json = JSONArray.toJSONString(goodsList);

				result = json.toString();

			}
		} catch (Exception e) {

			System.out.print(e.getMessage());

		} finally {
			/* 返回数据 */
			_response.setCharacterEncoding("UTF-8");

			_response.setHeader("content-type", "text/html;charset=UTF-8");

			PrintWriter pw = _response.getWriter();

			pw.write(result);

			pw.flush();

			pw.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
