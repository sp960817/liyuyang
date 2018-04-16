package com.myweb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myweb.domain.Goods;
import com.myweb.domain.GoodsType;
import com.myweb.utils.DBManager;

public class GoodsDao {

	public void addGoods(Goods goods) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "insert into goods (NAME,TYPEID,DESCRIPTION,STATE,IMGPATH,WEBPATH,FILEPATH,CREATEUSER,CREATEUSERID,ISDEL) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		params.add(goods.getState());

		params.add(goods.getImgpath());

		params.add(goods.getWebpath());

		params.add(goods.getFilepath());

		params.add(goods.getCreateuser());

		params.add(goods.getCreateuseid());

		params.add(goods.getIsdel());

		try {
			boolean flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getGoodsList() {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select a.* ,ifnull(t3.username,'管理员') as createuser from (SELECT t1.ID,t1.NAME,t2.TYPENAME,t1.DESCRIPTION,t1.IMGPATH,t1.FILEPATH,t1.CREATETIME,t1.CREATEUSERID from goods t1,goodstype t2 where t1.TYPEID=t2.ID and T1.ISDEL='0'  order by  t1.CREATETIME desc ) a left join user t3 on t3.id = a.CREATEUSERID";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getMyList(String userid) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select  a.* , t3.username as createuser from (SELECT t1.ID,t1.NAME,t2.TYPENAME,t1.DESCRIPTION,t1.IMGPATH,t1.FILEPATH,t1.CREATETIME,t1.CREATEUSERID from goods t1,goodstype t2 where t1.TYPEID=t2.ID and T1.ISDEL='0' AND t1.CREATEUSERID='"
				+ userid
				+ "' order by  t1.CREATETIME desc ) a left join user t3 on t3.id = a.CREATEUSERID";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getGoodsByNameAndType(String name,String type) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select a.* ,ifnull(t3.username,'管理员') as createuser from (SELECT t1.ID,t1.NAME,t2.TYPENAME,t1.DESCRIPTION,t1.IMGPATH,t1.FILEPATH,t1.CREATETIME,t1.CREATEUSERID from goods t1,goodstype t2 where t1.TYPEID=t2.ID and t1.name like '%"+name+"%' and t2.id='"
				+ type
				+ "' and T1.ISDEL='0'  order by  t1.CREATETIME desc ) a left join user t3 on t3.id = a.CREATEUSERID ";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public List<Map<String, Object>> getGoodsListByType(String goodsTypeId) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql2 = "select a.* ,ifnull(t3.username,'管理员') as createuser from (SELECT t1.ID,t1.NAME,t2.TYPENAME,t1.DESCRIPTION,t1.IMGPATH,t1.FILEPATH,t1.CREATETIME,t1.CREATEUSERID from goods t1,goodstype t2 where t1.TYPEID=t2.ID and t2.id='"
				+ goodsTypeId
				+ "' and T1.ISDEL='0'  order by  t1.CREATETIME desc ) a left join user t3 on t3.id = a.CREATEUSERID";

		List<Map<String, Object>> list = null;

		try {
			list = db.findModeResult(sql2, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;

	}

	public Goods getGoodsById(String id) {

		Goods goods = null;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "select a.id,a.name,a.typeid,a.typename,description,imgpath,filepath,ifnull(t3.username,'管理员') as createuser from (SELECT t1.ID,t1.NAME,T1.TYPEID,t2.TYPENAME,t1.DESCRIPTION,t1.IMGPATH,t1.FILEPATH,t1.CREATETIME,t1.CREATEUSERID from goods t1,goodstype t2 where t1.TYPEID=t2.ID   and t1.id='"
				+ id
				+ "' order by  t1.CREATETIME desc ) a left join user t3 on t3.id = a.CREATEUSERID";

		try {
			goods = (Goods) db.findSimpleRefResult(sql, null, Goods.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return goods;
	}

	public void delGoodsById(String id) {

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update  goods set isdel='1' where id=(?)";

		List<Object> list = new ArrayList<Object>();

		list.add(id);

		try {
			boolean flag = db.updateByPreparedStatement(sql, list);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean updateGoods(Goods goods) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update GOODS  set NAME=?,TYPEID=?,DESCRIPTION=?,IMGPATH=?,FILEPATH=? where id="
				+ String.valueOf(goods.getId());

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		params.add(goods.getImgpath());

		params.add(goods.getFilepath());

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateGoods1(Goods goods) {

		boolean flag = false;

		DBManager db = new DBManager();

		db.getConnection();

		String sql = "update GOODS  set  NAME=?,TYPEID=?,DESCRIPTION=? where id="
				+ String.valueOf(goods.getId());

		List<Object> params = new ArrayList<Object>();

		params.add(goods.getName());

		params.add(goods.getTypeid());

		params.add(goods.getDescription());

		try {
			flag = db.updateByPreparedStatement(sql, params);

			System.out.println(flag);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}
}
