package com.example.application.data.service;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;


@Service
public class SecurityUserDetailsService implements UserDetailsService { 
   @Autowired 
   private UserRepository userRepository;
   
   @Override 
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> user = userRepository.findByUsernameLike(username);
      if(user.isEmpty()) {
         throw new UsernameNotFoundException(username);
      }
      Set<GrantedAuthority> grantedAuthorities = new HashSet< >();
      grantedAuthorities.add(new SimpleGrantedAuthority(user.get().getRole().toUpperCase(Locale.ROOT)));
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return new org.springframework.security.core.userdetails.User(user.get().getUsername(), bCryptPasswordEncoder.encode(user.get().getPassword()),
              grantedAuthorities);
   }
}