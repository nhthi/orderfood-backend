package com.nht.Controller;

import com.nht.Model.Cart;
import com.nht.Model.USER_ROLE;
import com.nht.Model.User;
import com.nht.Model.Verify;
import com.nht.config.JwtProvider;
import com.nht.repository.CartRepository;
import com.nht.repository.UserRepository;
import com.nht.repository.VerifyRepository;
import com.nht.request.LoginRequest;
import com.nht.request.ResetPasswordRequest;
import com.nht.respone.AuthRespone;
import com.nht.respone.MessageResponse;
import com.nht.service.CustomerUserDetailService;
import com.nht.service.EmailService;
import com.nht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyRepository verifyRepository;



    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestParam("email") String email) throws Exception {
            User user = userService.findUserByEmail(email);
            String code = generateRandomNumber();
            Verify verify = verifyRepository.findByEmail(email);
            if(verify==null){
                verify = new Verify();
                verify.setEmail(email);
            }
            verify.setVerifyCode(code);
            verifyRepository.save(verify);
            emailService.sendEmail(email,"Verify Email","Your verification code is : "+code);
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Send verify code success");
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordRequest req) throws Exception {
        Verify verify = verifyRepository.findByEmail(req.getEmail());
        MessageResponse res = new MessageResponse();
        if(verify!=null){
            if(verify.getVerifyCode().equals(req.getCode())){
                    res.setMessage(UpdatePassword(req.getEmail(),req.getNewPassword()));
                    verifyRepository.delete(verify);
            }else
                res.setMessage("Invalid verification code");
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    public String UpdatePassword(String email,String newPassword) throws Exception {
        User user = userService.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        return "Update password success";
    }


    public static String generateRandomNumber() {
        Random random = new Random();
        int number = 1000 + random.nextInt(9000);
        return String.valueOf(number);
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthRespone> createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist != null){
            throw  new Exception("Email is already used with another account");
        }
        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());

        User savedUser = userRepository.save(createUser);
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthRespone authRespone = new AuthRespone();
        authRespone.setJwt(jwt);
        authRespone.setMessage("Register Success");
        authRespone.setRole(savedUser.getRole());
        return new ResponseEntity<>(authRespone, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthRespone> signin(@RequestBody LoginRequest req) throws Exception {

        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username,password);

        Collection<?extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthRespone authRespone = new AuthRespone();
        authRespone.setJwt(jwt);
        authRespone.setMessage("Signin Success");

        authRespone.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authRespone, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(username);
        if(userDetails==null){
            throw new Exception("Invalid Username ...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("Invalid password ..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
