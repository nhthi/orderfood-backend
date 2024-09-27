package com.nht.respone;

import com.nht.Model.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRespone {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
