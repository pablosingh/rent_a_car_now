package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Role;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.repository.RoleRepository;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionService permissionService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_rechazaEmailInvalido() {
        assertThatThrownBy(() -> userService.createUser("Ana", "Pérez", "no-es-email", "secret1", "USER", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ingresá un email válido.");
    }

    @Test
    void createUser_rechazaCamposVacios() {
        assertThatThrownBy(() -> userService.createUser("  ", "", " ", "12345", null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio. El apellido es obligatorio. El email es obligatorio. "
                        + "La contraseña debe tener al menos 6 caracteres.");
    }

    @Test
    void createUser_rechazaPasswordCorta() {
        assertThatThrownBy(() -> userService.createUser("Ana", "Pérez", "ana@mail.com", "abc12", "USER", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña debe tener al menos 6 caracteres.");
    }

    @Test
    void createUser_rechazaPasswordEnBlanco() {
        assertThatThrownBy(() -> userService.createUser("Ana", "Pérez", "ana@mail.com", "   ", "USER", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña es obligatoria.");
    }

    @Test
    void createUser_valido_guardaConTrimYRolPorDefecto() {
        Role userRole = new Role();
        userRole.setName("USER");
        when(userRepository.existsByEmail("ana@mail.com")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("secret1")).thenReturn("$2a$10$hash");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User created = userService.createUser("  Ana ", "  Pérez ", "  ana@mail.com  ", "secret1", null, null, null);

        verify(userRepository).existsByEmail("ana@mail.com");
        assertThat(created.getName()).isEqualTo("Ana");
        assertThat(created.getLastName()).isEqualTo("Pérez");
        assertThat(created.getEmail()).isEqualTo("ana@mail.com");
        assertThat(created.getPassword()).isEqualTo("$2a$10$hash");
        assertThat(created.getRole().getName()).isEqualTo("USER");
        assertThat(created.getVerified()).isTrue();
    }

    @Test
    void createEmployee_rechazaEmailInvalido() {
        User requester = new User();

        assertThatThrownBy(() -> userService.createEmployee("Ana", "Pérez", "mal-email", "secret1", null, null, requester))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ingresá un email válido.");
    }

    @Test
    void createEmployee_rechazaCamposVacios() {
        User requester = new User();

        assertThatThrownBy(() -> userService.createEmployee("", "Pérez", "ana@mail.com", "", null, null, requester))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio. La contraseña es obligatoria.");
    }
}
