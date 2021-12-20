package cz.mbucek.purkiadaserver.entities;

import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import cz.mbucek.purkiadaserver.entities.enums.ActionStatus;
import cz.mbucek.purkiadaserver.utilities.View.Extended;
import cz.mbucek.purkiadaserver.utilities.View.Public;

@Entity
@Table(name = "actions")
public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Public.class)
	private Long id;
	@JsonView(Public.class)
	private String name;
	@JsonView(Public.class)
	private String subName;
	@JsonView(Public.class)
	private String description;
	@JsonView(Public.class)
	private ZonedDateTime registrationStart;
	@JsonView(Public.class)
	private ZonedDateTime registrationEnd;
	@JsonView(Public.class)
	private ZonedDateTime actionStart;
	@JsonView(Public.class)
	private ZonedDateTime actionEnd;
	@JsonView(Public.class)
	private Integer maxUsers;
	@JsonView(Extended.class)
	private Boolean hidden;
	@JsonView(Extended.class)
	@OneToMany(mappedBy = "action", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ActionSubmit> submits;

	public Action() {

	}

	public Action(String name, String subName, String description, ZonedDateTime registrationStart,
			ZonedDateTime registrationEnd, ZonedDateTime actionStart, ZonedDateTime actionEnd, Integer maxUsers,
			Boolean hidden) {
		this.name = name;
		this.subName = subName;
		this.description = description;
		this.registrationStart = registrationStart;
		this.registrationEnd = registrationEnd;
		this.actionStart = actionStart;
		this.actionEnd = actionEnd;
		this.maxUsers = maxUsers;
		this.hidden = hidden;
	}

	public ActionStatus getStatus() {
		var now = ZonedDateTime.now();
		if (now.isBefore(registrationStart)) {
			return ActionStatus.BEFORE_REGISTRATION;
		} else if (now.isAfter(registrationStart) && now.isBefore(registrationEnd)) {
			return ActionStatus.REGISTRATION_IN_PROGRESS;
		} else if (now.isAfter(registrationEnd) && now.isBefore(actionStart)) {
			return ActionStatus.AFTER_REGISTRATION;
		} else if (now.isAfter(actionStart) && now.isBefore(actionEnd)) {
			return ActionStatus.ACTION_IN_PROGRESS;
		} else if (now.isAfter(actionEnd)) {
			return ActionStatus.AFTER_ACTION;
		}
		return null;
	}
	
	@JsonView(Public.class)
	public Integer freeSpace() {
		if(submits != null)
			return maxUsers - submits.size();
		return null;
	}

	@JsonIgnore
	public boolean hasFreeSpace() {
		return submits.size() < maxUsers;
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}
	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public Set<ActionSubmit> getSubmits() {
		return submits;
	}
	public void setSubmits(Set<ActionSubmit> submits) {
		this.submits = submits;
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
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ZonedDateTime getRegistrationStart() {
		return registrationStart;
	}
	public void setRegistrationStart(ZonedDateTime registrationStart) {
		this.registrationStart = registrationStart;
	}
	public ZonedDateTime getRegistrationEnd() {
		return registrationEnd;
	}
	public void setRegistrationEnd(ZonedDateTime registrationEnd) {
		this.registrationEnd = registrationEnd;
	}
	public ZonedDateTime getActionStart() {
		return actionStart;
	}
	public void setActionStart(ZonedDateTime actionStart) {
		this.actionStart = actionStart;
	}
	public ZonedDateTime getActionEnd() {
		return actionEnd;
	}
	public void setActionEnd(ZonedDateTime actionEnd) {
		this.actionEnd = actionEnd;
	}
}
