package br.com.ifba.conectaedu.util;

import br.com.ifba.conectaedu.entity.Administrador;
import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserUtil {

    public static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }

        return null;
    }

    public  boolean isAdminOfSchool(Escola escola){

        if (escola.getAdministradores().stream()
                .anyMatch(adm -> adm.getUsuario().getUsername().equals(getLoggedInUsername()))) {
            return true;
        }

        return false;
    }

}
