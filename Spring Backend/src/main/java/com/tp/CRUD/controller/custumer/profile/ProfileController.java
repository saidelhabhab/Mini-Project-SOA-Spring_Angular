package com.tp.CRUD.controller.custumer.profile;

import com.tp.CRUD.exception.ValidationException;
import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.service.client.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/client/")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @PutMapping("profile/{clientId}")
    public ResponseEntity<ClientDto> updateProfile(@RequestBody ClientDto clientDto, @PathVariable Long clientId) throws IOException {
        try {

            ClientDto updateClient = profileService.updateProfile(clientDto, clientId);
            return ResponseEntity.ok(updateClient);

        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("getProfileById/{clientId}")
    public  ResponseEntity<ClientDto> getProfileByID(@PathVariable Long clientId){

        try {
            ClientDto clientDto = profileService.getProfileById(clientId);
            return ResponseEntity.ok(clientDto);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
