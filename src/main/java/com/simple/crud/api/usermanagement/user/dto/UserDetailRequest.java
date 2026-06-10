package com.simple.crud.api.usermanagement.user.dto;

import com.simple.crud.api.usermanagement.user.enums.Gender;
import lombok.Data;

@Data
public class UserDetailRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String additionalInfo;
}
