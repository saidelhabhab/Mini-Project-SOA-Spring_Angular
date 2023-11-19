package com.tp.CRUD.service.admin.client;

import com.tp.CRUD.entity.Category;
import com.tp.CRUD.entity.Client;
import com.tp.CRUD.entity.Product;
import com.tp.CRUD.enums.UserRole;
import com.tp.CRUD.exception.ResourceNotFoundException;
import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.repository.ClientRepo;
import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminClientsServiceImpl implements AdminClientsService{

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> getAllClients = clientRepo.findClientsByRole(UserRole.CUSTOMER);

        if (getAllClients != null){
            return  getAllClients.stream().map(Client::getClientDto).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public boolean deleteClient(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (optionalClient.isPresent()){
            clientRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto, Long id) throws IOException {

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
    public ClientDto getClientById(Long id) throws IOException {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Client not found with id: " + id));

        // Convert Product entity to ProductDto
        return client.getClientDto();
    }
}
