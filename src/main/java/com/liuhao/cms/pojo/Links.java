package com.liuhao.cms.pojo;

import java.io.Serializable;
import java.util.Date;

public class Links implements Serializable{

	private Integer id;
	private String text;
	private String url;
	private Date created;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Links() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Links [id=" + id + ", text=" + text + ", url=" + url + ", created=" + created + "]";
	}
	public Links(Integer id, String text, String url, Date created) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.created = created;
	}
	
	
}
