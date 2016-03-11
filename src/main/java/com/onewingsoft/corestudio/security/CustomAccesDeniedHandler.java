package com.onewingsoft.corestudio.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
public class CustomAccesDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl = new AccessDeniedHandlerImpl();

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if (accessDeniedException instanceof CsrfException && !response.isCommitted()) {
            // Remove the session cookie so that client knows it's time to obtain a new CSRF token
            String pCookieName = "CSRF-TOKEN";
            Cookie cookie = new Cookie(pCookieName, "");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        accessDeniedHandlerImpl.handle(request, response, accessDeniedException);
    }

    /**
     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
     *
     * @param errorPage the dispatcher path to display
     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
     * @see AccessDeniedHandlerImpl#setErrorPage(String)
     */
    public void setErrorPage(String errorPage) {
        accessDeniedHandlerImpl.setErrorPage(errorPage);
    }
}
