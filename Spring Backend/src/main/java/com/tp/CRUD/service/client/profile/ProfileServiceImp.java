package com.tp.CRUD.service.client.profile;

import com.tp.CRUD.entity.Client;
import com.tp.CRUD.exception.ResourceNotFoundException;
import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.repository.ClientRepo;
import com.tp.CRUD.request.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProfileServiceImp  implements  ProfileService{

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public ClientDto updateProfile(ClientDto clientDto, Long id) throws IOException {

        Client client = clientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setPhone(clientDto.getPhone());
        client.setCity(clientDto.getCity());
        client.setAddress(clientDto.getAddress());

        // Save
        return clientRepo.save(client).getClientDto();
    }

    @Override
    public ClientDto getProfileById(Long id) throws IOException {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Client not found with id: " + id));

        // Convert Product entity to ProductDto
        return client.getClientDto();
    }
}
