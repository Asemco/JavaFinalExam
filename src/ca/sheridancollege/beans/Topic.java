package ca.sheridancollege.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private int id;
	private String tag;

	public String getTag() {
		return tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Topic(String tag) {
		this.tag = tag;
	}

	public Topic() {
	}

}
