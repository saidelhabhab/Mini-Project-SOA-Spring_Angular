package com.tp.CRUD.request;


import com.tp.CRUD.enums.UserRole;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String city;
    private UserRole role;
}
