package com.delivery.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDeliveryRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String notificationToken;
}
