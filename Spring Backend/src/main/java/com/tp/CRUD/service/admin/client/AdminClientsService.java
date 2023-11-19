package com.tp.CRUD.service.admin.client;

import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.request.ProductDto;
import com.tp.CRUD.request.SignupRequest;


import java.io.IOException;
import java.util.List;

public interface AdminClientsService {

    public List<ClientDto> getAllClients();

    public boolean deleteClient(Long id);

    public ClientDto updateClient(ClientDto clientDto, Long id) throws IOException;

    public ClientDto getClientById(Long id) throws IOException;

}
