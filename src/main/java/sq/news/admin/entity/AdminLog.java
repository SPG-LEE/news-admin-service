package sq.news.admin.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import sq.base.BaseEntity;
import sq.enumeration.LogType;

/**
 * 后台操作日志
 * 
 *
 * 
 */

@Entity
@Table(name = "admin_logs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class AdminLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Admin admin;// 操作人
	private String content;// 操作内容
	private LogType logType;// 操作类型
	private Long linkId;// 被操作对象Id

	@ManyToOne
	@JoinColumn(name = "adminId")
	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(final Admin admin) {
		this.admin = admin;
	}

	@Column(length = 300)
	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Enumerated(EnumType.STRING)
	public LogType getLogType() {
		return this.logType;
	}

	public void setLogType(final LogType logType) {
		this.logType = logType;
	}

	public Long getLinkId() {
		return this.linkId;
	}

	public void setLinkId(final Long linkId) {
		this.linkId = linkId;
	}

}
