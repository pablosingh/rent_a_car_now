package com.digitalhouse.rentacarnow.config;

import com.digitalhouse.rentacarnow.entity.Permission;
import com.digitalhouse.rentacarnow.entity.Role;
import com.digitalhouse.rentacarnow.repository.PermissionRepository;
import com.digitalhouse.rentacarnow.repository.RoleRepository;
import com.digitalhouse.rentacarnow.service.PermissionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class RbacSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RbacSeeder(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        Map<String, String> permissions = new LinkedHashMap<>();
        permissions.put(PermissionService.RESERVATION_CREATE, "Crear reservas propias");
        permissions.put(PermissionService.RESERVATION_VIEW_SCOPE, "Ver reservas según alcance del rol");
        permissions.put(PermissionService.CAR_CREATE, "Crear autos");
        permissions.put(PermissionService.CAR_EDIT, "Editar autos");
        permissions.put(PermissionService.CAR_DELETE, "Borrar autos");
        permissions.put(PermissionService.DISPATCH_RESERVATION, "Despachar reservas");
        permissions.put(PermissionService.COMPLETE_RESERVATION, "Completar reservas");
        permissions.put(PermissionService.CANCEL_ANY, "Cancelar cualquier reserva PENDING");
        permissions.put(PermissionService.REVERT_RESERVATION, "Revertir estados de reservas");
        permissions.put(PermissionService.EMPLOYEE_MANAGE, "Crear y gestionar empleados");
        permissions.put(PermissionService.OWNER_VERIFY, "Verificar cuentas OWNER");
        permissions.put(PermissionService.USER_ROLE_ASSIGN, "Asignar roles a usuarios");
        permissions.put(PermissionService.CATALOG_MANAGE, "Gestionar categorías y features");
        permissions.put(PermissionService.POLICY_MANAGE, "Gestionar políticas");
        permissions.put(PermissionService.RATING_MODERATE, "Moderar puntuaciones");
        if (roleRepository.count() == 4 && permissionRepository.count() >= permissions.size()) {
            return;
        }
        for (Map.Entry<String, String> e : permissions.entrySet()) {
            if (!permissionRepository.existsByCode(e.getKey())) {
                Permission p = new Permission();
                p.setCode(e.getKey());
                p.setDescription(e.getValue());
                permissionRepository.save(p);
            }
        }

        List<String> userPerms = List.of(
                PermissionService.RESERVATION_CREATE, PermissionService.RESERVATION_VIEW_SCOPE);
        List<String> employeePerms = List.of(
                PermissionService.RESERVATION_CREATE, PermissionService.RESERVATION_VIEW_SCOPE,
                PermissionService.CAR_CREATE, PermissionService.CAR_EDIT, PermissionService.CAR_DELETE,
                PermissionService.DISPATCH_RESERVATION, PermissionService.COMPLETE_RESERVATION,
                PermissionService.CANCEL_ANY, PermissionService.REVERT_RESERVATION);
        List<String> ownerPerms = List.of(
                PermissionService.RESERVATION_CREATE, PermissionService.RESERVATION_VIEW_SCOPE,
                PermissionService.CAR_CREATE, PermissionService.CAR_EDIT, PermissionService.CAR_DELETE,
                PermissionService.DISPATCH_RESERVATION, PermissionService.COMPLETE_RESERVATION,
                PermissionService.CANCEL_ANY, PermissionService.REVERT_RESERVATION,
                PermissionService.EMPLOYEE_MANAGE);
        List<String> adminPerms = List.copyOf(permissions.keySet());

        ensureRole("USER", "Reserva autos", userPerms);
        ensureRole("EMPLOYEE", "Gestiona autos y reservas de su OWNER", employeePerms);
        ensureRole("OWNER", "Gestiona sus autos, reservas y empleados", ownerPerms);
        ensureRole("ADMIN", "Control total y asignación de roles", adminPerms);
    }

    private void ensureRole(String name, String description, List<String> codes) {
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setDescription(description);
        }
        for (String code : codes) {
            Permission p = permissionRepository.findByCode(code).orElse(null);
            if (p != null) role.getPermissions().add(p);
        }
        roleRepository.save(role);
    }
}
