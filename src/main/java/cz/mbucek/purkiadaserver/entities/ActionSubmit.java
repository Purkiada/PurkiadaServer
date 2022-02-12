package cz.mbucek.purkiadaserver.entities;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import cz.mbucek.purkiadaserver.dtos.PublicUserDTO;
import cz.mbucek.purkiadaserver.utilities.HashUtils;
import cz.mbucek.purkiadaserver.utilities.View.Extended;
import cz.mbucek.purkiadaserver.utilities.View.Public;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Database model for an ActionSubmit.
 * It is the user's submission for an {@link Action}
 * 
 * @author Matěj Bucek
 *
 */
@Entity
@Table(name = "action_submit")
public class ActionSubmit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Public.class)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "action_id")
	private Action action;
	@Convert(converter = UserConverter.class)
	@JoinColumn(name = "user_id")
	private User user;
	@Transient
	@JsonView(Extended.class)
	private PublicUserDTO publicUser;
	@JsonView(Extended.class)
	private String legacyAccessToken;
	
	public PublicUserDTO getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUserDTO publicUser) {
		this.publicUser = publicUser;
	}

	public ActionSubmit() {
		
	}
	
	public ActionSubmit(Action action, User user) {
		this.action = action;
		this.user = user;
		this.legacyAccessToken = HashUtils.generateRandomPassword(10);
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

	public String getLegacyAccessToken() {
		return legacyAccessToken;
	}

	public void setLegacyAccessToken(String legacyAccessToken) {
		this.legacyAccessToken = legacyAccessToken;
	}
}
