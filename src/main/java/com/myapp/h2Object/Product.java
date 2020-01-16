package com.myapp.h2Object;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Product {
	@Id
	@Column(name = "PRODUCT_ID")
	private String id;
	
	private String name;
	
	@OneToMany(mappedBy = "product")
    private List<Orders> orders = new ArrayList<Orders>();
}
