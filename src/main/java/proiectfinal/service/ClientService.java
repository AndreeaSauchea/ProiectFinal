package proiectfinal.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proiectfinal.controller.dto.ClientRequest;
import proiectfinal.controller.dto.ClientResponse;
import proiectfinal.exception.ClientNotFoundException;
import proiectfinal.model.BookedRoom;
import proiectfinal.model.Client;
import proiectfinal.repository.BookedRoomRepository;
import proiectfinal.repository.ClientRepository;
import proiectfinal.utils.OptionalEntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ClientService {

    public static final String CLIENT_NOT_FOUND = "client not found";
    public static final String REQUEST_CAN_NOT_BE_NULL = "Request can not be null";
    public static final String THIS_CLIENT_HAS_NO_NAME = "This client has no name.";
    public static final String THIS_CLIENT_HAS_NO_FORENAME = "This client has no forename.";
    public static final String IT_IS_MANDATORY_TO_HAVE_CNP = "It is mandatory to have CNP.";
    public static final String YOU_HAVE_NOT_INTRODUCED_A_TYPE_ID = "You have not introduced a type ID";
    public static final String BOTH_CNP_AND_CLIENT_REQEST_ARE_NULL = "Both cnp and client reqest are null";
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookedRoomService bookedRoomService;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;


    public List<ClientResponse> findAll() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientResponse> clientResponseList = new ArrayList<>();
        for (Client addClient : clientList) {
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

    private Client findClient(Long clientId) throws Exception {
        Client client = new OptionalEntityUtils<Client>().getEntityOrException(clientRepository.findById(clientId),
                new ClientNotFoundException(CLIENT_NOT_FOUND));
        return client;
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
        if (clientRequest == null) {
            throw new IllegalArgumentException(REQUEST_CAN_NOT_BE_NULL);
        }
        if (Strings.isNullOrEmpty(clientRequest.getName())) {
            throw new IllegalArgumentException(THIS_CLIENT_HAS_NO_NAME);
        }
        if (Strings.isNullOrEmpty(clientRequest.getForename())) {
            throw new IllegalArgumentException(THIS_CLIENT_HAS_NO_FORENAME);
        }
        if (Strings.isNullOrEmpty(clientRequest.getCnp())) {
            throw new IllegalArgumentException(IT_IS_MANDATORY_TO_HAVE_CNP);
        }
    }

    public ClientResponse findById(Long id) throws Exception {
        return buildResponse(findClient(id));
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) throws Exception {
        Client client = findClient(id);
        buildClient(client, clientRequest);
        Client saveClient = clientRepository.save(client);
        return buildResponse(saveClient);
    }

    private Client buildClient(Client client, ClientRequest clientRequest) {
        client.setLastname(clientRequest.getName());
        client.setFirstname(clientRequest.getForename());
        client.setTypeID(clientRequest.getTypeID());
        client.setSeriesID(clientRequest.getSeriesID());
        client.setNumberID(clientRequest.getNumberID());
        client.setCnp(clientRequest.getCnp());
        client.setBirthday(clientRequest.getBirthday());
        client.setStreet(clientRequest.getStreet());
        client.setStreetNumber(clientRequest.getStreetNumber());
        return client;
    }

    public ClientResponse findByCnp(String cnp) throws Exception {
        if (Strings.isNullOrEmpty(cnp)) {
            throw new IllegalArgumentException(IT_IS_MANDATORY_TO_HAVE_CNP);
        } else {
            return buildByCnp(cnp);
        }
    }

    public ClientResponse updateByCnp(String cnp, ClientRequest clientRequest) throws Exception {
        if (Strings.isNullOrEmpty(cnp) || clientRequest == null) {
            throw new IllegalArgumentException(BOTH_CNP_AND_CLIENT_REQEST_ARE_NULL);
        }
        validate(clientRequest);
        Client client = clientRepository.findByCnp(cnp);
        if (client == null) {
            return save(clientRequest);
        } else {
            return updateClient(client.getId(), clientRequest);
        }
    }

    private ClientResponse buildByCnp(String cnp) throws Exception {
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
        response.setStreet(client.getStreet());
        response.setStreetNumber(client.getStreetNumber());
        BookedRoom bookedRoom = bookedRoomRepository.findFirstByClientAndCheckOutAfterOrderByCheckOutDesc(client, new Date());
        if (bookedRoom != null) {
            response.setRoom(bookedRoom.getRoom().getRoomNumber());
        }
        return response;
    }
}
