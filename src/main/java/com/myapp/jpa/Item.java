package com.myapp.jpa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(
		name = "ITEM_SEQ_GENERATOR",
		sequenceName = "ITEM_SEQ",
		initialValue = 1, allocationSize = 1
		)
@Getter @Setter
public abstract class Item {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE,
					generator = "ITEM_SEQ_GENERATOR")
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	private int price;
}
