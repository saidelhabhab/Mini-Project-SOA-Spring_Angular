package com.tp.CRUD.service;

import com.tp.CRUD.entity.Client;
import com.tp.CRUD.repository.ClientRepo;
import com.tp.CRUD.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class JwtService implements UserDetailsService {


    @Autowired
    private ClientRepo clientRepo;


    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Client> customer = clientRepo.findByEmail(username);

        if (customer.isEmpty()) throw new UsernameNotFoundException("Username is not valid",null);

        return new User(customer.get().getEmail(),customer.get().getPassword(),new ArrayList<>());

    }


}
