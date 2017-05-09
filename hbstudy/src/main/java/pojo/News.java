package pojo;

import java.util.Date;

public class News {
	
	private String id;

	private String title;
	
	private String content;
	
	private Date createDate;
	
	private String writer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", content=" + content
				+ ", createDate=" + createDate + ", writer=" + writer + "]";
	}
	
}
