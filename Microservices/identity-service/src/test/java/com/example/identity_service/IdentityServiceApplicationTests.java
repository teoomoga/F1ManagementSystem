package com.example.identity_service;

import com.example.identity_service.command.RegisterUserCommand;
import com.example.identity_service.command.RegisterUserCommandHandler;
import com.example.identity_service.model.entity.User;
import com.example.identity_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestResultExtension.class})
class RegisterUserCommandHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private RegisterUserCommandHandler handler;

    @Test
    void testRegisterUserWithRole() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setUsername("teomoga");
        cmd.setPassword("pass123");
        cmd.setEmail("teo@f1.com");
        cmd.setRole("admin");
        cmd.setManagedTeam(null);

        User saved = new User();
        saved.setUsername("teomoga");
        saved.setRole("ADMIN");
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = handler.handle(cmd);

        assertEquals("teomoga", result.getUsername());
        assertEquals("ADMIN", result.getRole());
        assertEquals("teo@f1.com", result.getEmail());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserDefaultsToGuest() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setUsername("visitor");
        cmd.setPassword("visitorpass");
        cmd.setEmail("v@f1.com");
        cmd.setRole(null);

        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = handler.handle(cmd);

        assertEquals("GUEST", result.getRole());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserEmptyRoleDefaultsToGuest() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setUsername("newuser");
        cmd.setPassword("pwd");
        cmd.setEmail("new@f1.com");
        cmd.setRole("");

        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = handler.handle(cmd);

        assertEquals("GUEST", result.getRole());
    }

    @Test
    void testRegisterUserWithManagedTeam() {
        RegisterUserCommand cmd = new RegisterUserCommand();
        cmd.setUsername("manager");
        cmd.setPassword("mgrpass");
        cmd.setEmail("mgr@f1.com");
        cmd.setRole("TEAM_MANAGER");
        cmd.setManagedTeam("Mercedes");

        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = handler.handle(cmd);

        assertEquals("TEAM_MANAGER", result.getRole());
        assertEquals("Mercedes", result.getManagedTeam());
    }
}
