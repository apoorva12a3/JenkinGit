package com.etl.security;
import com.etl.model.Users;
import com.etl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

//JwtUserDetailsService: This class implements the UserDetailsService
// interface to provide user-specific data.
// It retrieves user information from a data source and
// is used by JwtTokenUtil for user validation.
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Users getUser(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println(user.toString());
        }
        return user;
    }
}



