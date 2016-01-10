package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.CoreMessage;
import com.onewingsoft.corestudio.repository.MessageRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Business logic to control messages operations.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
@Service
public class MessageBusinessLogic {

    @Autowired
    private MessageRepository messageRepository;


    public CoreMessage deleteMessage(Long messageId) throws CorestudioException {
        CoreMessage message = messageRepository.findOne(messageId);
        if (message != null) {
            messageRepository.delete(message);
            return message;
        } else {
            throw new CorestudioException("El mensaje que quiere eliminar no existe");
        }
    }

    public CoreMessage readMessage(Long messageId) throws CorestudioException {
        CoreMessage message = messageRepository.findOne(messageId);
        if (message != null) {
            message.setIsRead(true);
            messageRepository.save(message);
            return message;
        } else {
            throw new CorestudioException("El mensaje que quiere leer no existe");
        }
    }

    public CoreMessage createMessage(CoreMessage message) {
        messageRepository.save(message);
        return message;
    }

    public Iterable<CoreMessage> getAll() {
        return messageRepository.findAll(new Sort(Sort.Direction.DESC, "date"));
    }
}
