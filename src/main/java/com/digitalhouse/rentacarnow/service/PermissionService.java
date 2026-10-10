package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Permission;
import com.digitalhouse.rentacarnow.entity.Role;
import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionService {

    public static final String RESERVATION_CREATE = "RESERVATION_CREATE";
    public static final String RESERVATION_VIEW_SCOPE = "RESERVATION_VIEW_SCOPE";
    public static final String CAR_CREATE = "CAR_CREATE";
    public static final String CAR_EDIT = "CAR_EDIT";
    public static final String CAR_DELETE = "CAR_DELETE";
    public static final String DISPATCH_RESERVATION = "DISPATCH_RESERVATION";
    public static final String COMPLETE_RESERVATION = "COMPLETE_RESERVATION";
    public static final String CANCEL_ANY = "CANCEL_ANY";
    public static final String REVERT_RESERVATION = "REVERT_RESERVATION";
    public static final String EMPLOYEE_MANAGE = "EMPLOYEE_MANAGE";
    public static final String OWNER_VERIFY = "OWNER_VERIFY";
    public static final String USER_ROLE_ASSIGN = "USER_ROLE_ASSIGN";
    public static final String CATALOG_MANAGE = "CATALOG_MANAGE";
    public static final String POLICY_MANAGE = "POLICY_MANAGE";
    public static final String RATING_MODERATE = "RATING_MODERATE";

    private static final Set<String> USER_LEVEL = Set.of(RESERVATION_CREATE, RESERVATION_VIEW_SCOPE);

    public String roleName(User user) {
        if (user == null || user.getRole() == null) return null;
        return user.getRole().getName();
    }

    public boolean hasRole(User user, String role) {
        return role != null && role.equals(roleName(user));
    }

    public boolean isAdmin(User user) {
        return hasRole(user, "ADMIN");
    }

    public boolean hasPermission(User user, String code) {
        if (user == null || user.getRole() == null) return false;
        Role role = user.getRole();
        if (role.getPermissions() == null) return false;
        boolean granted = role.getPermissions().stream()
                .map(Permission::getCode)
                .anyMatch(code::equals);
        if (!granted) return false;
        if ("OWNER".equals(role.getName()) && !Boolean.TRUE.equals(user.getVerified())
                && !USER_LEVEL.contains(code)) {
            return false;
        }
        return true;
    }

    public void require(User user, String code) {
        if (!hasPermission(user, code)) {
            if ("OWNER".equals(roleName(user)) && !Boolean.TRUE.equals(user.getVerified())
                    && !USER_LEVEL.contains(code)) {
                throw new AccessDeniedException("Tu cuenta de OWNER está pendiente de verificación.");
            }
            throw new AccessDeniedException("No tenés el permiso " + code + ".");
        }
    }

    public boolean isStaff(User user) {
        return hasPermission(user, DISPATCH_RESERVATION)
                || hasPermission(user, COMPLETE_RESERVATION)
                || isAdmin(user);
    }
}
