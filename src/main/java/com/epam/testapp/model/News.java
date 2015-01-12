package com.epam.testapp.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

/**
 * News --- model class which describes news with fields id, title, date, brief,
 * content and implements interface Serializable
 * 
 * @author Marharyta_Amelyanchuk
 */
@NamedQueries({
		@NamedQuery(name = "getAllOrderByDateAndId", query = "from News news order by news.date desc"),
		@NamedQuery(name = "deleteByArrayOfId", query = "delete News where id in(:ids)") })
@Entity
@Table(name = "NEWS")
public class News implements Serializable {

	private static final long serialVersionUID = -8046656654608054181L;
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "news_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "ID")
	private int id;
	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;
	@Column(name = "DAY", nullable = false)
	private Date date;
	@Column(name = "BRIEF", nullable = false, length = 500)
	private String brief;
	@Column(name = "CONTENT", nullable = false, length = 2000)
	private String content;

	public News() {
	}

	public News(String title, Date date, String brief, String content) {
		this.title = title;
		this.date = date;
		this.brief = brief;
		this.content = content;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
