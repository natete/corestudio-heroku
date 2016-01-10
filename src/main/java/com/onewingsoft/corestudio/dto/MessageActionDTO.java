package com.onewingsoft.corestudio.dto;

import com.onewingsoft.corestudio.model.CoreMessage;
import com.onewingsoft.corestudio.utils.Action;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
public class MessageActionDTO {

    private CoreMessage message;
    private Action action;

    public MessageActionDTO(CoreMessage message, Action action) {
        this.message = message;
        this.action = action;
    }

    public CoreMessage getMessage() {
        return message;
    }

    public void setMessage(CoreMessage message) {
        this.message = message;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
