package ca.sheridancollege.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PostBlock {
	
	@Id
	@GeneratedValue
	private int id;
	private String header;
	@Column(columnDefinition="LONGTEXT")
	private String text;
	private Date datePosted;
	private String postedBy;

	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}
	
	public PostBlock(String header, String text, Date datePosted, String postedBy) {
		this.header = header;
		this.text = text;
		this.datePosted = datePosted;
		this.postedBy = postedBy;
	}
	
	public PostBlock(String header, String text, Date datePosted) {
		this.header = header;
		this.text = text;
		this.datePosted = datePosted;
	}
	public PostBlock(int id, String header, String text) {
		this.id = id;
		this.header = header;
		this.text = text;
	}
	
	public PostBlock(String header, String text) {
		this.header = header;
		this.text = text;
	}
	public PostBlock() {
	}
}
