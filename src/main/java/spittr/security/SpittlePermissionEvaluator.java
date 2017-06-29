package spittr.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import spittr.Spittle;

import java.io.Serializable;

/**
 * Created by dell on 2017-6-28.
 */
public class SpittlePermissionEvaluator implements PermissionEvaluator {
    private static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_SPITTER");

    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        if (target instanceof Spittle) {
            Spittle spittle = (Spittle)target;
            if ("delete".equals(permission)) {
                return isAdmin(authentication) && spittle.getMessage().length() < 100;
            }
        }

        throw new UnsupportedOperationException("hasPermission not supported for object <" + target + "> and permission <" + permission + ">");
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().contains(ADMIN_AUTHORITY);
    }
}
