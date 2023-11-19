package com.tp.CRUD.service.authservice;


import com.tp.CRUD.entity.Client;
import com.tp.CRUD.entity.Facture;
import com.tp.CRUD.enums.OrderStatus;
import com.tp.CRUD.enums.UserRole;
import com.tp.CRUD.repository.ClientRepo;
import com.tp.CRUD.repository.FactureRepo;
import com.tp.CRUD.request.ClientDto;
import com.tp.CRUD.request.SignupRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private FactureRepo factureRepo;

    @Override
    public ClientDto createCustomer(SignupRequest signupRequest){
        Client client = new Client();

        client.setEmail(signupRequest.getEmail());
        client.setFirstName(signupRequest.getFirstName());
        client.setLastName(signupRequest.getLastName());
        client.setAddress(signupRequest.getAddress());
        client.setPhone(signupRequest.getPhone());
        client.setCity(signupRequest.getCity());
        client.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        client.setRole(UserRole.CUSTOMER);

        Client createUser= clientRepo.save(client);

        ///////////////Facture  ////////////////////
        Facture facture = new Facture();
        facture.setAmount(0L);
        facture.setTotalAmount(0L);
        facture.setDiscount(0L);
        facture.setClient(createUser);
        facture.setProduct(null);
        facture.setOrderStatus(OrderStatus.Pending);
        factureRepo.save(facture);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(createUser.getId());

        return clientDto;

    }

    @Override
    @PostConstruct
    public ClientDto createAdminAccount() {
        Client admin = clientRepo.findByRole(UserRole.ADMIN);
        if (admin== null){
            Client client= new Client();
            client.setFirstName("admin");
            client.setLastName("admin");
            client.setPhone("060000000010");
            client.setCity("Kenitra");
            client.setEmail("admin@admin.ma");
            client.setPassword(passwordEncoder.encode("12345"));
            client.setRole(UserRole.ADMIN);

            clientRepo.save(client);
        }

        return null;
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return clientRepo.findByEmail(email).isPresent();
    }
}
