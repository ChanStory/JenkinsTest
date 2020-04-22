package com.myapp.security;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.myapp.advice.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    
    private String refreshSecretKey; //리프레쉬 토큰 암호화 키
 
    private long accessTokenValidMilisecond = 1000L * 60 * 60; //토큰 유효시간 1시간
    
    private long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24 * 14; //refresh토큰 유효기간 2주일
    
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
		this.refreshSecretKey = properties.getProperty("spring.jwt.refresh.secret");
    }
    
    /**
	 * jwt토큰 생성
	 * 
	 * @param String userPk
	 * @param List<String> roles
	 * @param String type
	 * @return String token
	 */
    public String createToken(String userPk, List<String> roles, String type) {
    	String key = type.equals("access") ? secretKey : refreshSecretKey;
    	long tokenValidMilisecond = type.equals("access") ? accessTokenValidMilisecond : refreshTokenValidMilisecond;
    	
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //데이터
                .setIssuedAt(now) //토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) //Expire Time
                .signWith(SignatureAlgorithm.HS256, key) //암호화 알고리즘, secret값
                .compact();
    }

    /**
	 * jwt 토큰으로 인증 정보 조회
	 * 
	 * @param String token
	 * @param String type
	 * @return Authentication
	 */
    public Authentication getAuthentication(String token, String type) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token, type));
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
 
    /**
	 * jwt 토큰에서 회원 구별 정보 추출
	 * 
	 * @param String token
	 * @param String type
	 * @return String
	 */
    public String getUserPk(String token, String type){
    	String key = type.equals("access") ? secretKey : refreshSecretKey;
    	String userPk = null;
    	
    	try {
    		userPk = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
        	throw new TokenExpiredException();
        }
    	
        return userPk;
    }
 
    /**
	 * X-AUTH-TOKEN 파싱
	 * 
	 * @param HttpServletRequest request
	 * @return String
	 */
    public String resolveAccessToken(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	if(cookies != null) {
    		for(Cookie cookie : cookies) {  
    			System.out.println("cookie name : " + cookie.getName() + ", value : " + cookie.getValue() 
    			+ ", path : " + cookie.getPath() + ", domain : " + cookie.getDomain() + ", maxAge : " + cookie.getMaxAge());
//        		if(cookie.getName().equals("X-AUTH-TOKEN")) {
//        			return cookie.getValue();
//        		}
        	}
    	}
    	
        return null;
    }
 
    /**
	 * X-AUTH-REFRESH-TOKEN 파싱
	 * 
	 * @param HttpServletRequest request
	 * @return String
	 */
    public String resolveRefreshToken(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	if(cookies != null) {
    		for(Cookie cookie : cookies) {    		
        		if(cookie.getName().equals("X-AUTH-REFRESH-TOKEN")) {
        			return cookie.getValue();
        		}
        	}
    	}
    	
        return null;
    }
    
    /**
	 * jwt 토큰의 유효성, 만료일자 확인
	 * 
	 * @param String jwtToken
	 * @param String type
	 * @return boolean
	 * @exception TokenExpiredException
	 */
    public boolean validateToken(String jwtToken, String type) {
        try {
        	String key = type.equals("access") ? secretKey : refreshSecretKey;
        	
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            
            boolean tokenExpired = claims.getBody().getExpiration().before(new Date());
            
            //토큰의 만료일자가 지났을 때
            if(tokenExpired) {
            	throw new TokenExpiredException();
            }
            
            return !tokenExpired;
        } catch (Exception e) {
            return false;
        }
    }
}