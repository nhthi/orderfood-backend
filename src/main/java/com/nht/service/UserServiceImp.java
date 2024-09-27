package com.nht.service;

import com.nht.Model.User;
import com.nht.config.JwtProvider;
import com.nht.repository.UserRepository;
import com.nht.request.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User  user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User updateUser(UpdateUser updateUser,String jwt) throws Exception {
        User user = findUserByJwtToken(jwt) ;
        if(updateUser.getAvatar() != null){
            user.setAvatar(updateUser.getAvatar());
        }
        if(updateUser.getFullName()!=null){
            user.setFullName(updateUser.getFullName());
        }
        if(updateUser.getEmail()!=null){
            user.setEmail(updateUser.getEmail());
        }
        return userRepository.save(user);
    }
}
