package com.android.domain;

import java.io.Serializable;

public class Question  implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String content;
	private String imgpath;
	private String createusername;
	private int createuserid;
	private String createtime;
	private String state;
	private String replaycontent;
	private String replayusername;
	private String replaytime;
	private int replayuserid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getCreateusername() {
		return createusername;
	}
	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	public int getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(int createuserid) {
		this.createuserid = createuserid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReplaycontent() {
		return replaycontent;
	}
	public void setReplaycontent(String replaycontent) {
		this.replaycontent = replaycontent;
	}
	public String getReplayusername() {
		return replayusername;
	}
	public void setReplayusername(String replayusername) {
		this.replayusername = replayusername;
	}
	public String getReplaytime() {
		return replaytime;
	}
	public void setReplaytime(String replaytime) {
		this.replaytime = replaytime;
	}
	public int getReplayuserid() {
		return replayuserid;
	}
	public void setReplayuserid(int replayuserid) {
		this.replayuserid = replayuserid;
	}
	 
	 
}
