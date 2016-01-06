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
