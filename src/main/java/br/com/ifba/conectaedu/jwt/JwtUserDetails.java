package br.com.ifba.conectaedu.jwt;

import br.com.ifba.conectaedu.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {
    private Usuario usuario;
    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }
    public Long getId(){
        return this.usuario.getId();
    }
    public String getRole(){
        return this.usuario.getRole().name();
    }
}