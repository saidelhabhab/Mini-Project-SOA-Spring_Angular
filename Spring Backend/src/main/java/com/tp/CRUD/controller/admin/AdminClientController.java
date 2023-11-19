package com.tp.CRUD.controller.admin;


import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.request.ProductDto;
import com.tp.CRUD.request.SignupRequest;
import com.tp.CRUD.service.admin.client.AdminClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminClientController {

    @Autowired
    private AdminClientsService clientsService;

    @GetMapping("clients")
    public ResponseEntity<List<ClientDto>> getClients(){
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @DeleteMapping("client/{clientId}")
    public  ResponseEntity<Void> deleteClient(@PathVariable Long clientId){

        boolean delete = clientsService.deleteClient(clientId);
        if (delete) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("client/{clientId}")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto, @PathVariable Long clientId) throws IOException {
        try {

            ClientDto updateClient = clientsService.updateClient(clientDto, clientId);
            return ResponseEntity.ok(updateClient);

        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("getClientByID/{clientId}")
    public  ResponseEntity<ClientDto> getClientByID(@PathVariable Long clientId){

        try {
            ClientDto clientDto = clientsService.getClientById(clientId);
            return ResponseEntity.ok(clientDto);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
