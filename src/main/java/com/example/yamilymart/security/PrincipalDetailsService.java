package com.example.yamilymart.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.yamilymart.dao.YamilyDao;
import com.example.yamilymart.dto.HrDTO;
import com.example.yamilymart.dto.User;

import lombok.RequiredArgsConstructor;

//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행된다.
//http://localhost:8080/login 요청이 올 때 이 PrincipalDetailsService의 loadUserByUsername가 동작한다!
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private YamilyDao yDao;

	 @Autowired
	 public PrincipalDetailsService(YamilyDao yDao) {
	     this.yDao = yDao;
	 }
	 
     @Autowired
     private UserRepository userRepository;

//	 @Override
//	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	
//	     Optional<User> userEntity = userRepository.findByUsername(username);
//	     if (userEntity.isPresent()) {
//	    	 User user = userEntity.get();
//	            return new PrincipalDetails(user.getUsername(),user.getPassword(), user.getRole());
//	     }
//	     throw new UsernameNotFoundException("User not found with username: " + username);
//	 }
     
     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         if (username == null || username.isEmpty()) {
             throw new UsernameNotFoundException("Username is empty");
         }
         
         Optional<User> userEntity = this.userRepository.findByUsername(username);
         
         if (userEntity.isPresent()) {
             User user = userEntity.get();
             
//             List<GrantedAuthority> authorities = new ArrayList<>();
//             if ("admin".equals(username)) {
//            	 authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
//             } else {
//            	 authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
//             }
             
             return new PrincipalDetails(user.getUsername(), user.getPassword(), user.getRole());
         }
         throw new UsernameNotFoundException("User not found with username: " + username);
     }
	 
	 
	 
}
