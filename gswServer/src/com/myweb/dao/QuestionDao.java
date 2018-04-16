package com.myweb.dao;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myweb.domain.News;
import com.myweb.domain.Question;
import com.myweb.utils.DBManager;

public class QuestionDao {

	public void addQuestion(Question question) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into question ( title,content,imgpath,createusername,createuserid,createtime,state) values (?, ?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(question.getTitle());

		params.add(question.getContent());

		params.add(question.getImgpath());

		params.add(question.getCreateusername());

		params.add(question.getCreateuserid());

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		params.add(format.format(new Date()));

		params.add("未解答");

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean updateQuestion(Question question) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update Question  set  replaycontent=?,state=?,replaytime=? where id="
				+ String.valueOf(question.getId());

		List<Object> params = new ArrayList<Object>();

		params.add(question.getReplaycontent());

		params.add("已解答");

		params.add(question.getReplaytime());

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	// 查询所有问题
	public List<Map<String, Object>> getQuestionList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "SELECT t1.id,t1.title,t1.content,t1.createtime,t1.state,t2.USERNAME as createusername from QUESTION  t1,user t2 where t1.createuserid=t2.id order by CREATETIME desc";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	// 删除问题
	public void delQuestionById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "delete from QUESTION where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public Question getQuestionById(String id) {

		Question question = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select *  from Question where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			question = (Question) db.findSimpleRefResult(sql, list,
					Question.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return question;
	}
}
