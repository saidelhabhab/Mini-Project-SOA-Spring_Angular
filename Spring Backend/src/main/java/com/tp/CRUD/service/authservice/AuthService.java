package com.tp.CRUD.service.authservice;


import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.request.SignupRequest;

public interface AuthService {

    public ClientDto createCustomer(SignupRequest signupRequest);
    public ClientDto createAdminAccount();
    public Boolean hasUserWithEmail(String email);
}
