package com.tp.CRUD.request;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest {

    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String phone;
    private String address;
    private String city;
}
