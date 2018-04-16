package com.myweb.servlet;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.dao.QuestionDao;

import com.myweb.domain.Question;

public class QuestServlet extends HttpServlet {
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

		if (action.equals("list")) {

			listQuest();

		} else if (action.equals("edit")) {

			editQuest();

		} else if (action.equals("editSave")) {

			editSaveQuest();

		} else if (action.equals("delete")) {
			deleteQuest();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void deleteQuest() throws ServletException, IOException {
		String id = _request.getParameter("id");

		QuestionDao dao = new QuestionDao();

		try {
			dao.delQuestionById(id);

			_request.setAttribute("alertNote", "1");

		} catch (Exception ex) {

			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/QuestServlet?action=list&").forward(
				_request, _response);
	}

	public void editQuest() throws ServletException, IOException {

		String id = _request.getParameter("id");

		QuestionDao dao = new QuestionDao();

		Question question = null;

		try {
			question = dao.getQuestionById(id);

			_request.setAttribute("question", question);

		} catch (Exception ex) {

		}
		_request.getRequestDispatcher("/admin/question_Edit.jsp").forward(
				_request, _response);
	}

	public void editSaveQuest() throws ServletException, IOException {

		String replaycontent = _request.getParameter("replaycontent");

		String id = _request.getParameter("id");

		QuestionDao dao = new QuestionDao();

		Question question = null;

		try {
			question = dao.getQuestionById(id);

			question.setReplaycontent(replaycontent);

			question.setState("已解答");

			Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			question.setReplaytime(format.format(new Date()));

			boolean flag = dao.updateQuestion(question);
			if (flag) {
				_request.setAttribute("alertNote", "1");
			} else {
				_request.setAttribute("alertNote", "0");
			}
		} catch (Exception ex) {
			_request.setAttribute("alertNote", "0");
		}

		_request.getRequestDispatcher("/QuestServlet?action=edit&id=" + id)
				.forward(_request, _response);
	}

	public void listQuest() throws ServletException, IOException {

		QuestionDao dao = new QuestionDao();
		try {
			List<Map<String, Object>> list = dao.getQuestionList();

			_request.setAttribute("QuestionList", list);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		_request.getRequestDispatcher("/admin/question_List.jsp").forward(
				_request, _response);

	}
}
