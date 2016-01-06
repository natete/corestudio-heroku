package com.onewingsoft.corestudio.utils;

import org.springframework.http.HttpHeaders;

public class HeaderUtil {

	public static HttpHeaders createAlert(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-corestudioApp-alert", message);
//		headers.add("X-corestudioApp-params", param);
		return headers;
	}

	public static HttpHeaders createEntityAlert(String message) {
		return createAlert("Se ha creado " + message);
	}

	public static HttpHeaders updateEntityAlert(String message) {
		return createAlert("Se ha actualizado " + message);
	}

	public static HttpHeaders deleteEntityAlert(String message) {
		return createAlert("Se ha eliminado " + message);
	}

	public static HttpHeaders errorAlert(String message) {
		return createAlert(message);
	}
}