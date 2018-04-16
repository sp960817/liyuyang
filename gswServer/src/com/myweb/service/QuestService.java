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
import com.myweb.dao.QuestionDao;
import com.myweb.dao.UserDao;
import com.myweb.domain.Question;
import com.myweb.domain.User;

public class QuestService extends HttpServlet {

	HttpSession _session;

	HttpServletRequest _request;

	HttpServletResponse _response;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		_session = request.getSession();

		_request = request;

		_response = response;

		String action = "";

		action = request.getParameter("action").toString();

		if (action.equals("add")) {

			addQuest();

		} else if (action.equals("list")) {

			listQuest();

		} else if (action.equals("view")) {

			viewQuest();

		}  else if (action.equals("delete")) {

			deleteQuest();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void listQuest() throws ServletException, IOException {

		String result = "";// 返回结果集

		try {

			QuestionDao questionDao = new QuestionDao();

			List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();

			questionList = questionDao.getQuestionList();

			if (questionList != null) {

				String json = JSONArray.toJSONString(questionList);

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

	public void viewQuest() throws ServletException, IOException {

		String result = "";// 返回结果集

		String id = _request.getParameter("questid").toString();

		try {

			QuestionDao questionDAO = new QuestionDao();

			Question question = new Question();

			question = questionDAO.getQuestionById(id);

			if (question != null) {
				result = JSONObject.toJSONString(question);
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

	public void addQuest() throws ServletException, IOException {

		String question = _request.getParameter("question").toString();

		question = new String(question.getBytes("ISO8859-1"), "UTF-8");

		String result = "";// 返回结果集

		try {

			Question questObj = JSONObject
					.parseObject(question, Question.class);

			QuestionDao questDAO = new QuestionDao();

			questDAO.addQuestion(questObj);

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


	public void deleteQuest() throws ServletException, IOException {

		String result = "";// 返回结果集

		String id = _request.getParameter("id").toString();

		QuestionDao questDAO = new QuestionDao();

		try {

			questDAO.delQuestionById(id);

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
}
