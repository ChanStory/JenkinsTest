package com.myapp.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.myapp.common.RoleType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Team {
	
	@Id
	@Column(name = "TEAM_ID")
	private String id;
	
	private String name;
}
