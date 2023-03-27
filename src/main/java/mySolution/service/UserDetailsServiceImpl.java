package mySolution.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsServiceImpl {
    UserDetails loadUserByUsername(String id,String password) throws UsernameNotFoundException;
}