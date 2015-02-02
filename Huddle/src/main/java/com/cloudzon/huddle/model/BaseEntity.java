package com.cloudzon.huddle.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;

import com.cloudzon.huddle.util.DateUtil;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * All objects will have a unique UUID which allows for the decoupling from
	 * DB generated ids
	 * 
	 */
	@Column(length = 36)
	private String uuid;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "modified_by", nullable = false)
	private User modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	public BaseEntity() {
		this(UUID.randomUUID());
		this.createdDate = DateUtil.getCurrentDate();
	}

	public BaseEntity(UUID guid) {
		Assert.notNull(guid, "UUID is required");
		setUuid(guid.toString());
		this.createdDate = DateUtil.getCurrentDate();
	}

	public UUID getUuid() {
		return UUID.fromString(uuid);
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	/**
	 * In most instances we can rely on the UUID to identify the object.
	 * Subclasses may want a user friendly identifier for constructing easy to
	 * read urls
	 * 
	 * <code>
	 *    /user/1883c578-76be-47fb-a5c1-7bbea3bf7fd0 using uuid as the identifier
	 * 
	 *    /user/jsmith using the username as the identifier
	 * 
	 * </code>
	 * 
	 * @return Object unique identifier for the object
	 */
	public Object getIdentifier() {
		return getUuid().toString();
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
