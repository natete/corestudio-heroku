package com.onewingsoft.corestudio.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
public class PrincipalDTO {

    private String userName;

    private Collection<String> authorities;

    public PrincipalDTO(String userName, Collection<GrantedAuthority> authorities) {
        this.userName = userName;
        this.authorities = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            this.authorities.add(authority.getAuthority());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }
}
