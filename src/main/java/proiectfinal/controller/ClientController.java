package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.model.Client;
import proiectfinal.service.ClientService;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> findClients() {

        return clientService.findAll();

    }

    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client newClient) {
        return clientService.save(newClient);
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable Long id) {

        return clientService.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    @PutMapping("/clients/{id}")
    Client updateClient(@RequestBody Client newClient, @PathVariable Long id) {

        return clientService.updateClient(id, newClient);
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
    }


}
