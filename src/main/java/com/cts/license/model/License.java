package com.cts.license.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class License extends RepresentationModel<License> {
	@Id
	private String licenseId;
	private String description;
	private String organizationId;
	private String productName;
	private String licenseType;
	private String comment;
	@Transient
	private String organizationName;
	@Transient
	private String contactName;
	@Transient
	private String contactPhone;
	@Transient
	private String contactEmail;

	public License withComment(String comment) {
		this.setComment(comment);
		return this;
	}

}
