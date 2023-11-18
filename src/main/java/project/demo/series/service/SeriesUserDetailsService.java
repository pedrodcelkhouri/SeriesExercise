package project.demo.series.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class SeriesUserDetailsService implements UserDetailsService {
//    private final SeriesUserRepository seriesUserRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username){
//        return Optional.ofNullable(seriesUserRepository.findByUsername(username))
//                .orElseThrow(() -> new UsernameNotFoundException("Series User nor found"));
//    }
//}
