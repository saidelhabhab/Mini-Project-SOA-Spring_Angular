package com.tp.CRUD.service.client.profile;

import com.tp.CRUD.request.ClientDto;

import java.io.IOException;

public interface ProfileService {

    public ClientDto updateProfile(ClientDto clientDto, Long id) throws IOException;

    public ClientDto getProfileById(Long id) throws IOException;
}
