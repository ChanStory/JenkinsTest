package com.myapp.h2Object;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


import com.myapp.common.RoleType;

import lombok.Getter;
import lombok.Setter;
/**
 * User: HolyEyE
 * Date: 13. 5. 24. Time: 오후 7:43
 */
@Entity
@Getter @Setter
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(
		name = "ID_UNIQUE",
		columnNames = "MEMBER_ID")}
		)
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1, allocationSize = 1
		)
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    				generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private Integer age;
    
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    @Lob
    private String description;
    
    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<Orders>();
    
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    
    @Embedded
    Period workPeriod;
    
    @Embedded
    Address homeAddress;
    
    public void setTeam(Team team) {
    	if(this.team != null) {
    		this.team.getMembers().remove(this);
    	}
    	this.team = team;
    	team.getMembers().add(this);
    }
}