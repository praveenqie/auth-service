package com.qie.developer.security.entity;


import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
@Data
public class Timestamps {

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	private Date modifiedOn;

	private String createdBy;
	
	private String modifiedBy;


}
