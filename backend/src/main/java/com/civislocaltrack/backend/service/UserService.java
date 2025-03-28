package com.civislocaltrack.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.civislocaltrack.backend.model.User;
import com.civislocaltrack.backend.model.Role;
import com.civislocaltrack.backend.repository.RoleRepository;
import com.civislocaltrack.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByLibelleRole("CITOYEN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setLibelleRole("CITOYEN");
                    return roleRepository.save(newRole);
                });

        user.setRole(role);        
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByMail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setMail(user.getMail());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setTel(user.getTel());
            userToUpdate.setName(user.getName());
            userToUpdate.setRole(user.getRole());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
}
