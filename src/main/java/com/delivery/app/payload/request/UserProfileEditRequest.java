package com.delivery.app.payload.request;

import lombok.Data;

@Data
public class UserProfileEditRequest {
    private String firstname;
    private String lastname;
//    private String username;
    private String phone;
}
