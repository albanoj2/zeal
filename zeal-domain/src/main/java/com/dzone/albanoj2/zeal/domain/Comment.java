package com.dzone.albanoj2.zeal.domain;

/**
 * A comment posted to an article.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 * 
 * @since 1.0.0
 */
public class Comment {

	private long id;
	private long creationTime;
	private String content;
	
	public Comment() {}
	
	public Comment(long id, long creationTime, String content) {
		this.id = id;
		this.creationTime = creationTime;
		this.content = content;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
