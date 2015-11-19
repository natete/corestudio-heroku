package com.onewingsoft.corestudio.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onewingsoft.corestudio.business.ClientBusinessLogic;
import com.onewingsoft.corestudio.model.Client;
import com.onewingsoft.corestudio.utils.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ClientRestService {

	@Autowired
	private ClientBusinessLogic clientBusinessLogic;

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public Iterable<Client> getAllClients() {
		return clientBusinessLogic.getAllClients();
	}

	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	public ResponseEntity<Client> getClient(@PathVariable Long id) {
		Client client = clientBusinessLogic.getClient(id);
		if (null != client) {
			return ResponseEntity.ok().body(client);
		} else {
			return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/clients", method = RequestMethod.POST)
	public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {

		if (client.getId() != null) {
			return ResponseEntity.badRequest().header("Un nuevo cliente no puede tener valor de identificador")
					.body(null);
		} else {
			Client result = clientBusinessLogic.createClient(client);
			return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
					.headers(HeaderUtil.createEntityCreationAlert("cliente", result.getId().toString())).body(result);
		}
	}
}
