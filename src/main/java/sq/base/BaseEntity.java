package sq.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.news.admin.constants.SysConstants;
import sq.enumeration.EntityStatus;
import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "id")
	@JsonProperty
	private Long id;
	@JsonProperty
	private Date createDate = new Date();
	@JsonProperty
	private Date updateDate = new Date();

	private final String className;
	@ApiModelProperty(value = "状态")
	@JsonProperty
	private EntityStatus entityStatus = EntityStatus.ENABLE;

	public BaseEntity() {
		this.className = this.getClass().getSimpleName().toLowerCase();
	}

	@Transient
	public String getClassName() {
		return this.className;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	public EntityStatus getEntityStatus() {
		return this.entityStatus;
	}

	public void setEntityStatus(final EntityStatus status) {
		this.entityStatus = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public boolean equals(final Object obj) {

		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj instanceof BaseEntity) {
			final BaseEntity other = (BaseEntity) obj;
			if (other.getId() == null)
				return false;
			else
				return other.getId().equals(this.getId());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (getId() == null)
			return super.hashCode();
		else
			return getId().hashCode();
	}

	@Transient
	public boolean getEnable() {
		return this.entityStatus != null && this.entityStatus.equals(EntityStatus.ENABLE);
	}

	@Transient
	public void setEnable(final boolean value) {
		this.entityStatus = value ? EntityStatus.ENABLE : EntityStatus.DISABLE;
	}

	@Transient
	public String getStatusName() {
		switch (this.entityStatus) {
		case ENABLE:
			return SysConstants.STATUS_ENALBE;
		case DISABLE:
			return SysConstants.STATUS_DISABLE;
		case DELETE:
			return SysConstants.STATUS_DELETE;
		default:
			return SysConstants.STATUS_UNKOWN;
		}
	}

}
