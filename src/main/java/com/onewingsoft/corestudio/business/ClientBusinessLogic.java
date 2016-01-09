package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDTO;
import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Client;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic to manage clients.
 */
@Service
public class ClientBusinessLogic extends BaseBusinessLogic<Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    PassBusinessLogic passBusinessLogic;

    /**
     * @see BaseBusinessLogic#getAllEntities().
     * @return list of {@link ClientDTO} that includes pass information.
     */
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

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Client client) throws IllegalArgumentException {
        if(client.getName() == null) {
            throw new IllegalArgumentException("Un cliente debe tener un nombre");
        }
        if(client.getFirstSurname() == null) {
            throw new IllegalArgumentException("Un cliente debe tener un primer apellido");
        }
        if(client.getFirstPhone() == null) {
            throw new IllegalArgumentException("Un cliente debe tener al menos un teléfono principal");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<Client, Long> getRepository() {
        return clientRepository;
    }
}
