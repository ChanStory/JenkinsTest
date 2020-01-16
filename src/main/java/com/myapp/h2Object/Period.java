package com.myapp.h2Object;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Period {
	
	@Temporal(TemporalType.DATE)
	Date startDate;
	
	@Temporal(TemporalType.DATE)
	Date endDate;
}
