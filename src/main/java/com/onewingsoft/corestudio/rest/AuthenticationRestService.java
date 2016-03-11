package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.AuthenticationBusinessLogic;
import com.onewingsoft.corestudio.dto.PasswordDTO;
import com.onewingsoft.corestudio.dto.PrincipalDTO;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
@RestController
@RequestMapping("api/auth")
public class AuthenticationRestService {

    @Autowired
    private AuthenticationBusinessLogic authenticationBusinessLogic;

    @RequestMapping(value="/changePassword", method = RequestMethod.POST)
    public ResponseEntity<PrincipalDTO> changePassword(@RequestBody PasswordDTO passwordDTO) {
        try {
            PrincipalDTO result = authenticationBusinessLogic.changePassword(passwordDTO);
            try {
                return ResponseEntity.created(new URI(this.getUri()))
                        .headers(HeaderUtil.updateEntityAlert(this.getMessage(result)))
                        .body(result);
            } catch (URISyntaxException e) {
                LoggerUtil.writeErrorLog(e.getMessage(), e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<PrincipalDTO> getPrincipal() {
        PrincipalDTO principal = authenticationBusinessLogic.getPrincipal();
        if (null != principal) {
            return ResponseEntity.ok().body(principal);
        } else {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert("La entidad buscada no existe"))
                    .body(null);
        }
    }

    private String getMessage(PrincipalDTO principalDTO) {
        return " la contraseña del profesor " + principalDTO.getUserName();
    }

    public String getUri() {
        return "/api/password";
    }
}
