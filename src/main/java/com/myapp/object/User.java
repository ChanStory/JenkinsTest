package com.myapp.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 유저 엔티티
 * 
 * @author chans
 */

@Builder //builder를 사용할수 있게 함
@Entity
@Getter @Setter
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "USER")
public class User implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_NUMBER")
    private long msrl; //유저 넘버
	
    @Column(name = "USER_ID", nullable = false, unique = true, length = 30)
    private String uid; //유저 아이디
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //직렬화 시 해당 필드를 제외시켜 json응답에 미포함 됨
    @Column(nullable = false, length = 100)
    private String password; //비밀번호
    
    @Column(nullable = false, length = 100)
    private String name; //이름
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>(); //권한
 
    
    //getAuthorities 메서드 재정의 호출 시 권한을 부여, 반환해줌
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
 
    //getUsername 메서드 재정의 호출시 유저아이디를 반환해줌
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }
 
    //계정 만료를 체크할 필요가 없어 항상 true를 리턴해주게 함
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    //계정 잠김 미체크
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    //패스워드 만료 미체크
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    //계정 사용 가능여부 미체크
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}