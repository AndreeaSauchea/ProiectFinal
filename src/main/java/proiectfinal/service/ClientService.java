package proiectfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.model.Client;
import proiectfinal.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {

        return (List<Client>) clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }

    public Client updateClient(Long id, Client newClient) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setCheckIn(newClient.getCheckIn());
            client.setCheckOut(newClient.getCheckIn());
            client.setBirthday(newClient.getBirthday());
            client.setCnp(newClient.getCnp());
            client.setForename(newClient.getForename());
            client.setName(newClient.getName());
            client.setNumberID(newClient.getNumberID());
            client.setSeriesID(newClient.getSeriesID());
            client.setStreet(newClient.getStreet());
            client.setStreetNumber(newClient.getStreetNumber());
            client.setTypeID(newClient.getTypeID());
            return clientRepository.save(client);
        } else {
            throw new ClientNotFoundException();
        }
    }
}
