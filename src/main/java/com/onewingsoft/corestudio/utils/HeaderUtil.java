package com.onewingsoft.corestudio.utils;

import org.springframework.http.HttpHeaders;

public class HeaderUtil {

	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-corestudioApp-alert", message);
		headers.add("X-corestudioApp-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
		return createAlert("Se ha creado un nuevo " + entityName + " con identificador " + param, param);
	}

	public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
		return createAlert("Se ha actualizado un " + entityName + " con identificador " + param, param);
	}

	public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
		return createAlert("Se ha eliminado un " + entityName + " con identificador " + param, param);
	}
}