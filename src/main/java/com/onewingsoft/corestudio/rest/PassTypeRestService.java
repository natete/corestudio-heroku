package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.PassTypeBusinessLogic;
import com.onewingsoft.corestudio.model.PassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@RestController
@RequestMapping("api/admin/passTypes")
public class PassTypeRestService extends BaseRestService<PassType> {

    @Autowired
    private PassTypeBusinessLogic businessLogic;

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return businessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/admin/passTypes";
    }

    @Override
    protected String getMessage(Object entity) {
        return " el tipo de abono " + entity.toString();
    }
}
