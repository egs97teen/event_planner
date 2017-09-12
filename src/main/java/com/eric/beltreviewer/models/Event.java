package com.eric.beltreviewer.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="events")
public class Event {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=1)
	private String name;
	
	private Date date;
	
	@Size(min=1)
	private String city;
	
	@Size(min=1)
	private String state;
	
	@OneToMany(mappedBy="event", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Comment> comments;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User host;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
			name = "users_events",
			joinColumns = @JoinColumn(name = "event_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
    
	@CreationTimestamp
	private Date createdAt;
	private Date updatedAt;
	
	public Event() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDate() {
		return date;
	}

	public String getDateString() {
		Date eventDate = this.date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(eventDate);
		return dateString;
	}

	public void setDate(String date) {
		if (date.equals("")) {
			this.date = null;
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date eventDate;
		
			try {
				eventDate = formatter.parse(date);
				this.date = eventDate;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setCreatedDate(String createdAt) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date eventDate;
	
		try {
			eventDate = formatter.parse(createdAt);
			this.date = eventDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
    protected void onCreate(){
    		this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    		this.setUpdatedAt(new Date());
    }
    
    public String dateFormat() {
    		Date date = this.getDate();
    		SimpleDateFormat format = new SimpleDateFormat("MMMMM d, yyyy");
    		String eventDate = format.format(date);
    		return eventDate;
    }

}
