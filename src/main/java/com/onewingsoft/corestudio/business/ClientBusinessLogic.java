package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDTO;
import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Client;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.ClientRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
     * @return list of {@link ClientDTO} that includes pass information.
     * @see BaseBusinessLogic#getAllEntities(Integer, Integer, String, String) .
     */
    public Page<ClientDTO> getAllDtos(Integer page, Integer size, String sortBy, String direction) {
        Page<Client> clients = super.getAllEntities(page, size, sortBy, direction);
        List<ClientDTO> result = new ArrayList<>();

        for (Client client : clients.getContent()) {
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

        Sort sort = null;
        if (sortBy != null) {
            sort = new Sort(Sort.Direction.fromString(direction), sortBy);
        }
        Pageable pageRequest = new PageRequest(page, size, sort);

        Page<ClientDTO> pageDTO = new PageImpl<>(result, pageRequest, clients.getTotalElements());

        return pageDTO;
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Client client) throws CorestudioException {
        if (client.getName() == null) {
            throw new CorestudioException("Un cliente debe tener un nombre");
        }
        if (client.getFirstSurname() == null) {
            throw new CorestudioException("Un cliente debe tener un primer apellido");
        }
        if (client.getFirstPhone() == null) {
            throw new CorestudioException("Un cliente debe tener al menos un teléfono principal");
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
