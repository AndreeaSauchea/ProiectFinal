package proiectfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proiectfinal.controller.dto.ClientHistoryResponse;
import proiectfinal.controller.dto.ClientRequest;
import proiectfinal.controller.dto.ClientResponse;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.model.History;
import proiectfinal.service.ClientService;

import java.util.List;

@RestController
@CrossOrigin
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
    public ClientResponse updateClient(@RequestBody ClientRequest newClientRequest, @PathVariable Long id) throws ClientNotFoundException {
        return clientService.updateClient(id, newClientRequest);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
    }

    @CrossOrigin
    @GetMapping("/clients/cnp/{cnp}")
    public ClientResponse findByCnp(@PathVariable String cnp) throws BookedRoomNotFoundException {
        return clientService.findByCnp(cnp);
    }


}
