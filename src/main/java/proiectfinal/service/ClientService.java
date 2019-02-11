package proiectfinal.service;

import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.ClientRequest;
import proiectfinal.controller.dto.ClientResponse;
import proiectfinal.exception.BookedRoomNotFoundException;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.model.Client;
import proiectfinal.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookedRoomService bookedRoomService;


    public List<ClientResponse> findAll() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientResponse> clientResponseList = new ArrayList<>();
        for (Client addClient : clientList){
            clientResponseList.add(buildResponse(addClient));
        }

        return clientResponseList;
    }

    private ClientResponse buildResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setForename(client.getFirstname());
        response.setName(client.getLastname());
        return response;
    }

     Client findClient(Long clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()){
            return optionalClient.get();
        } else {
            throw new ClientNotFoundException("client not found");
        }
    }

    public ClientResponse save(ClientRequest clientRequest) {
        validate(clientRequest);
        Client client = new Client();
        buildClient(client, clientRequest);
        client.setStreetNumber(clientRequest.getStreetNumber());
        client.setStreet(clientRequest.getStreet());
        Client saveClient = clientRepository.save(client);
        return buildResponse(saveClient);
    }

    private void validate(ClientRequest clientRequest) {
        if (clientRequest.getName() == null){
            throw new IllegalArgumentException("This client has no name.");
        }
        if (clientRequest.getForename() == null){
            throw new IllegalArgumentException("This client has no forename.");
        }
        if (clientRequest.getCnp() == null){
            throw new IllegalArgumentException("It is mandatory to have CNP.");
        }
    }

    public ClientResponse findById(Long id) throws ClientNotFoundException {
        return buildResponse(findClient(id));
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) throws ClientNotFoundException {
        Client client = findClient(id);
        buildClient(client, clientRequest);
        Client saveClient = clientRepository.save(client);
        return buildResponse(saveClient);
    }

    public Client buildClient(Client client, ClientRequest clientRequest){
        client.setLastname(clientRequest.getName());
        client.setFirstname(clientRequest.getForename());
        client.setTypeID(clientRequest.getTypeID());
        client.setSeriesID(clientRequest.getSeriesID());
        client.setNumberID(clientRequest.getNumberID());
        client.setCnp(clientRequest.getCnp());
        client.setBirthday(clientRequest.getBirthday());
        return client;
    }

    public ClientResponse findByCnp(String cnp) throws BookedRoomNotFoundException {
        return buildByCnp(cnp);
    }

    public ClientResponse updateByCnp(String cnp, ClientRequest clientRequest) {
        Client client = clientRepository.findByCnp(cnp);
        buildClient(client, clientRequest);
        Client saveClient = clientRepository.save(client);
        return buildResponse(saveClient);
    }

    public ClientResponse buildByCnp(String cnp) throws BookedRoomNotFoundException {
        ClientResponse response = new ClientResponse();
        Client client = clientRepository.findByCnp(cnp);
        response.setName(client.getLastname());
        response.setForename(client.getFirstname());
        response.setBirthday(client.getBirthday());
        response.setCnp(cnp);
        response.setTypeID(client.getTypeID());
        response.setSeriesID(client.getSeriesID());
        response.setNumberID(client.getNumberID());
        response.setId(client.getId());
        response.setRoom(bookedRoomService.findBookedRoomByClient(client.getId()).getRoom());
        return response;
    }
}
