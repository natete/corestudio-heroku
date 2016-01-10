package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ClientBusinessLogic;
import com.onewingsoft.corestudio.dto.ClientDTO;
import com.onewingsoft.corestudio.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientRestService extends BaseRestService<Client> {

    @Autowired
    private ClientBusinessLogic clientBusinessLogic;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Page<ClientDTO> getAllClients(Integer page, Integer size, String sortBy, String direction) {
        return clientBusinessLogic.getAllDtos(page, size, sortBy, direction);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return clientBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/clients/";
    }

    @Override
    protected String getMessage(Object client) {
        return "el cliente " + client.toString();
    }
}
