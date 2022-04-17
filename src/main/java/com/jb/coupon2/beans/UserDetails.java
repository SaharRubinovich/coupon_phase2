package com.jb.coupon2.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private int id;
    private String userEmail;
    private String userPass;
    private UserType userType;
}
