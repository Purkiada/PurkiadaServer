package cz.mbucek.purkiadaserver.entities;

import javax.persistence.Table;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "action_submit")
public class ActionSubmit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "action_id")
	private Action action;
	@Convert(converter = UserConverter.class)
	@JoinColumn(name = "user_id")
	private User user;
	
	public ActionSubmit() {
		
	}
	
	public ActionSubmit(Action action, User user) {
		this.action = action;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
