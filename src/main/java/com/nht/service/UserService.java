package com.nht.service;

import com.nht.Model.User;
import com.nht.request.UpdateUser;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws  Exception;
    public User findUserByEmail(String email) throws  Exception;
    public User updateUser(UpdateUser updateUser,String jwt) throws  Exception;
}
