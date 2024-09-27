package com.nht.Controller;

import com.nht.Model.User;
import com.nht.request.UpdateUser;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
            User user = userService.findUserByJwtToken(jwt);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestHeader("Authorization") String jwt,
                                              @RequestBody UpdateUser updateUser) throws Exception {
        User user = userService.updateUser(updateUser,jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
