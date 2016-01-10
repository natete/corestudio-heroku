package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.MessageBusinessLogic;
import com.onewingsoft.corestudio.model.CoreMessage;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageRestService {

    @Autowired
    private MessageBusinessLogic messageBusinessLogic;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<CoreMessage> getAllMessages() {
        return messageBusinessLogic.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CoreMessage> readMessage(@PathVariable Long id) {
        try {
            CoreMessage message = messageBusinessLogic.readMessage(id);
            return ResponseEntity.created(new URI("/api/messages"))
                    .headers(HeaderUtil.updateEntityAlert("Mensaje leído " + message.toString()))
                    .body(message);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CoreMessage> deleteMessage(@PathVariable Long id) {
        try {
            CoreMessage message = messageBusinessLogic.deleteMessage(id);
            return ResponseEntity.created(new URI("/api/messages"))
                    .headers(HeaderUtil.deleteEntityAlert("Mensaje eliminado " + message.toString()))
                    .body(null);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }
}
