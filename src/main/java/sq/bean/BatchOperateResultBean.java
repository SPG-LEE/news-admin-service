package sq.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchOperateResultBean {
	@JsonProperty
	private String number;
	@JsonProperty
	private String reason;

	public BatchOperateResultBean(String number, String message) {
		this.number = number;
		this.reason = message;
	}

	public BatchOperateResultBean() {
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
