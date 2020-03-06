package com.myapp.security;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT 토큰 생성 및 검증 클래스
 * 
 * @author chans
 */

@Component
public class JwtTokenProvider {

    private String secretKey; //암호화 키
 
    private long tokenValidMilisecond = 1000L * 60 * 60; //토큰 유효시간 1시간
    
    private UserDetailsService userDetailsService;
    
    /**
	 * 생성자
	 * 객체가 만들어질 때 secretKey 값은 외부 설정파일에서 가져오도록 함
	 * 
	 * @param UserDetailsService userDetailsService
	 */
    public JwtTokenProvider(UserDetailsService userDetailsService) throws IOException {
    	this.userDetailsService = userDetailsService;
    	
		FileReader resources = new FileReader("DB_JWT_Info.properties");
		Properties properties = new Properties();
		
		properties.load(resources);
		
		this.secretKey = properties.getProperty("spring.jwt.secret");
    }
    
    /**
	 * jwt토큰 생성
	 * 
	 * @param String userPk
	 * @param List<String> roles
	 * @return String token
	 */
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //데이터
                .setIssuedAt(now) //토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) //Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) //암호화 알고리즘, secret값
                .compact();
    }
 
    /**
	 * jwt 토큰으로 인증 정보 조회
	 * 
	 * @param String token
	 * @return Authentication
	 */
    public Authentication getAuthentication(String token) {
    	
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
 
    /**
	 * jwt 토큰에서 회원 구별 정보 추출
	 * 
	 * @param String token
	 * @return String
	 */
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
 
    /**
	 * X-AUTH-TOKEN 파싱
	 * 
	 * @param HttpServletRequest req
	 * @return String
	 */
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }
 
    /**
	 * jwt 토큰의 유효성, 만료일자 확인
	 * 
	 * @param String jwtToken
	 * @return boolean
	 */
    public boolean validateToken(String jwtToken) {
        try {
        	
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}