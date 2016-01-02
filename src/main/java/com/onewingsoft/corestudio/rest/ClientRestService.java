package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ClientBusinessLogic;
import com.onewingsoft.corestudio.dto.ClientDTO;
import com.onewingsoft.corestudio.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ClientRestService extends BaseRestService<Client> {

	@Autowired
	private ClientBusinessLogic clientBusinessLogic;

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public Iterable<ClientDTO> getAllClients() {
		return clientBusinessLogic.getAllClients();
	}

	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	public ResponseEntity<Client> getClient(@PathVariable Long id) {
		return super.getEntity(id);
	}

	@RequestMapping(value = "/clients", method = RequestMethod.POST)
	public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
		return super.saveEntity(client);
	}

	@RequestMapping(value = "/clients", method = RequestMethod.PUT)
	public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
		return super.updateEntity(client);
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
	protected String getEntityName() {
		return " cliente ";
	}

	@Override
	protected String getParameter(Client entity) {
		return entity.getName() + " " + entity.getFirstSurname();
	}
}
