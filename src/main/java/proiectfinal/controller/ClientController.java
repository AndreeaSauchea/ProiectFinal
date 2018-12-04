package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.ClientRequest;
import proiectfinal.controller.dto.ClientResponse;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.service.ClientService;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<ClientResponse> findClients() {
        return clientService.findAll();
    }

    @PostMapping("/clients")
    public ClientResponse saveClient(@RequestBody ClientRequest newClientRequest) {
        return clientService.save(newClientRequest);
    }

    @GetMapping("/clients/{id}")
    public ClientResponse getClientById(@PathVariable Long id) throws ClientNotFoundException {
        return clientService.findById(id);
    }

    @PutMapping("/clients/{id}")
    ClientResponse updateClient(@RequestBody ClientRequest newClientRequest, @PathVariable Long id) throws ClientNotFoundException {
        return clientService.updateClient(id, newClientRequest);
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
    }


}
