package com.tp.CRUD.entity;


import com.tp.CRUD.enums.UserRole;
import com.tp.CRUD.request.ClientDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String city;
    private UserRole role;
    @Lob
    private byte[] img;


    @OneToMany(mappedBy = "client")
    private Set<Facture> facture = new HashSet<>();


    public ClientDto getClientDto(){
        ClientDto clientDto = new ClientDto();
        clientDto.setId(id);
        clientDto.setFirstName(firstName);
        clientDto.setLastName(lastName);
        clientDto.setEmail(email);
        clientDto.setAddress(address);
        clientDto.setCity(city);
        clientDto.setPhone(phone);
        clientDto.setRole(role);

        return  clientDto;
    }
}
