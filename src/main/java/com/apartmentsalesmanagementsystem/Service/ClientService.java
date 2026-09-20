package com.apartmentsalesmanagementsystem.Service;

import com.apartmentsalesmanagementsystem.Entity.Client;
import com.apartmentsalesmanagementsystem.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void registerClient(Client client) {
        // In a production app, encrypt the password here using BCrypt before saving
        clientRepository.save(client);
    }

    public Client authenticateClient(String email, String password) {
        Client client = clientRepository.findByEmail(email);
        if (client != null && client.getPassword().equals(password)) {
            return client;
        }
        return null;
    }
    public void updateClient(Client client) {
        clientRepository.save(client); // Assuming clientRepository is your JpaRepository
    }
}