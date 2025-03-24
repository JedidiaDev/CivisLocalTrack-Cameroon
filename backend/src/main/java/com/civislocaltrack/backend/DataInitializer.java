package com.civislocaltrack.backend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.civislocaltrack.backend.model.Role;
import com.civislocaltrack.backend.model.User;
import com.civislocaltrack.backend.repository.RoleRepository;
import com.civislocaltrack.backend.repository.UserRepository;
import com.civislocaltrack.backend.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si l'utilisateur existe déjà
        Optional<User> existingUser = userRepository.findByName("admin");

        if (existingUser.isEmpty()) {
            // Si l'utilisateur n'existe pas, créer un nouvel utilisateur
            User user = new User();
            Role role = new Role();
            role.setLibelleRole("CITOYEN");
            roleRepository.save(role);
            user.setName("admin");
            user.setPassword("admin123");
            user.setMail("admin@afroleadership.org");
            user.setTel(699920831);
            user.setRole(role);
            userService.registerUser(user);
            System.out.println("Utilisateur 'admin' ajouté à la base de données.");
        } else {
            System.out.println("L'utilisateur 'admin' existe déjà.");
        }

        // Vérifier si l'utilisateur est bien présent
        User foundUser = userRepository.findByName("admin").orElse(null);
        if (foundUser != null) {
            System.out.println("Utilisateur trouvé : " + foundUser.getName());
        } else {
            System.out.println("Utilisateur 'admin' non trouvé.");
        }
    }
}
