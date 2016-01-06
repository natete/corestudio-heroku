package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ClientBusinessLogic;
import com.onewingsoft.corestudio.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientRestService extends BaseRestService<Client> {

	@Autowired
	private ClientBusinessLogic clientBusinessLogic;

//	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
//	public Iterable<ClientDTO> getAllClients() {
//		return clientBusinessLogic.getAllClients();
//	}

//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Client> getClient(@PathVariable Long id) {
//		return super.getEntity(id);
//	}

//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
//		return super.saveEntity(client);
//	}

//	@RequestMapping(method = RequestMethod.PUT)
//	public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
//		return super.updateEntity(client);
//	}

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
