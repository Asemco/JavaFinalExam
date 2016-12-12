package ca.sheridancollege.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2241103072212107034L;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@Column(columnDefinition="LONGTEXT")
	private String message;
	private String topic;
	private Date datePosted;
	private Date dateEdited;
	private String editedBy;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<PostBlock> postBlocks;
	
	
	
	public Post(String name, String message, String topic, Date datePosted, Date dateEdited, String editedBy,
			List<PostBlock> postBlocks) {
		this.name = name;
		this.message = message;
		this.topic = topic;
		this.datePosted = datePosted;
		this.dateEdited = dateEdited;
		this.editedBy = editedBy;
		this.postBlocks = postBlocks;
	}

	public Post(String name, String message, String topic, Date dateEdited, String editedBy,
			List<PostBlock> postBlocks) {
		this.name = name;
		this.message = message;
		this.topic = topic;
		this.dateEdited = dateEdited;
		this.editedBy = editedBy;
		this.postBlocks = postBlocks;
	}

	public Post(String name, String topic, Date dateEdited, String editedBy, List<PostBlock> postBlocks) {
		this.name = name;
		this.topic = topic;
		this.dateEdited = dateEdited;
		this.editedBy = editedBy;
		this.postBlocks = postBlocks;
	}

	public Post(String name, String topic) {
		this.name = name;
		this.topic = topic;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Date getDateEdited() {
		return dateEdited;
	}
	public void setDateEdited(Date dateEdited) {
		this.dateEdited = dateEdited;
	}
	public List<PostBlock> getPostBlocks() {
		return postBlocks;
	}
	public void setPostBlocks(List<PostBlock> postBlocks) {
		this.postBlocks = postBlocks;
	}
	public Post(String topic, Date dateEdited, List<PostBlock> postBlocks) {
		this.topic = topic;
		this.dateEdited = dateEdited;
		this.postBlocks = postBlocks;
	}
	public Post() {
	}
	
}
