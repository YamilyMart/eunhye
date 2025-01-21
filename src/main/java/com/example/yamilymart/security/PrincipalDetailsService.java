package com.example.yamilymart.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
	     Optional<User> userEntity = yDao.findByUsername(username);
	     if (userEntity.isPresent()) {
	    	 User user = userEntity.get();
	            return new PrincipalDetails(user.getUsername(),user.getPassword(), user.getRole());
	     }
	     throw new UsernameNotFoundException("User not found with username: " + username);
	 }
	 
	 
	 
}
