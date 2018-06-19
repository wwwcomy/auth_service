package com.iteye.wwwcomy.authservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name = Token.TABLE_NAME)
public class Token extends BasePersistedObject {
	public static final String TABLE_NAME = "T_TOKEN";
	private String content;
	private String md5Content;

	public Token() {
	}

	public Token(String content) {
		this.content = content;
		this.md5Content = DigestUtils.md5Hex(content.getBytes());
	}

	@Column(length = 2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMd5Content() {
		return md5Content;
	}

	public void setMd5Content(String md5Content) {
		this.md5Content = md5Content;
	}
}
