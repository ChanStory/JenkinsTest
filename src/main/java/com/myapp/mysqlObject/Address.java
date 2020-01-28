package com.myapp.mysqlObject;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {
	
	@Column(name = "city")
	private String city;
	
	private String street;
	
	private String zipCode;
	
}
