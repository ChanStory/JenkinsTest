package com.myapp.entity;

import java.util.*;
import java.util.stream.Collectors;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

import io.swagger.annotations.*;

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
@ToString //toString 메서드를 override 해주어 객체 주소가 아니라 프로퍼티값들을 보여줌
@ApiModel(value = "User")
@Table(name = "USER")
public class User implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true) //swagger문서에 프로퍼티값이 안보이게 함, 유저 객체를 반환해줄 때 msrl값은 같이 넘어가야하기 때문
	@Column(name = "NUMBER")
    private long msrl; //유저 넘버
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]{5,15}$") //영대.소문자, 숫자만 사용해서 5~15자리
    @Column(name = "ID", nullable = false, unique = true, length = 15)
    private String uid; //유저 아이디
    
	@NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //직렬화 시 해당 필드를 제외시켜 json응답에 미포함 됨
    @Column(nullable = false, length = 15)
    private String password; //비밀번호
    
	@NotNull
    @Column(nullable = false, length = 50)
    private String name; //이름
    
	@Past //입력받은 날짜값이 과거의 날짜인지 체크
    @JsonFormat(pattern = "yyyyMMdd") //json으로 넘어오는 String을 yyyyMMdd 포맷으로 받기위해
    private LocalDate birthDate; //생년월일
    
	@Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$") //01(0,1,6,7,8,9) - 3~4자리숫자 - 4자리숫자
    private String phoneNumber; //전화번호
    
	@NotNull
    private String address; //주소
    
    @Email //이메일형식에 맞는지 체크
    private String email; //이메일
    
    
    @ApiModelProperty(hidden = true)
    @ElementCollection(fetch = FetchType.EAGER) //user가 없이 user_roles 만 있는게 의미가 없기때문에 ElementCollection으로 설정
    @CollectionTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name="USER_NUMBER")
      )
    private List<String> roles; //권한
 
    
    //getAuthorities 메서드 재정의 호출 시 권한리스트를 반환해줌
    @Override
    @JsonIgnore
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
    
    //권한 종류
    public enum Roles {
		ROLE_ADMIN,
		ROLE_USER
    }
}