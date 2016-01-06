package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDTO;
import com.onewingsoft.corestudio.model.Client;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientBusinessLogic extends BaseBusinessLogic<Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    PassBusinessLogic passBusinessLogic;

    @Override
    public Iterable<ClientDTO> getAllEntities() {
        Iterable<Client> clients = (Iterable<Client>) super.getAllEntities();
        List<ClientDTO> result = new ArrayList<>();

        for (Client client : clients) {
            ClientDTO dto = new ClientDTO();
            dto.setId(client.getId());
            dto.setName(client.getName());
            dto.setFirstSurname(client.getFirstSurname());
            Pass currentPass = passBusinessLogic.getCurrentPass(client.getId());
            if (currentPass != null) {
                dto.setLastDate(currentPass.getLastDate());
                dto.setPendingSessions(currentPass.getPendingSessions());
            }
            result.add(dto);
        }

        return result;
    }

    @Override
    protected Client processEntity(Client client) {
        return client;
    }

    @Override
    protected void validateEntity(Client client) throws IllegalArgumentException {

    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return clientRepository;
    }
}
