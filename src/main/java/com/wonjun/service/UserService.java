package com.wonjun.service;

import com.wonjun.model.entity.BoardUser;
import com.wonjun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BoardUser create(String username, String email, String password) {
        BoardUser user = new BoardUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }

    public BoardUser getUser(String name) throws DataNotFoundException { // name 이지만 사실상 ID로 찾는거임
        Optional<BoardUser> boardUser = userRepository.findByUsername(name);
        if(boardUser.isPresent()){
            return boardUser.get();
        }
        else throw new DataNotFoundException("user not found");
    }
}
