package com.civislocaltrack.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.civislocaltrack.backend.service.UserService;
import com.civislocaltrack.backend.model.*;

@SpringBootTest
class CivisLocalTrackCameroonApplicationTests {

	// @Test
	// void contextLoads() {
	// }
	@Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setName("Jeanot");
        user.setMail("jean@test.com");
        user.setPassword("123456");

        userService.registerUser(user);

        Assertions.assertNotNull(user.getId());  // Il a bien été sauvegardé
		System.out.println("User registered successfully");
    }

	// @Test
	// public void testGetUser() {
	// 	User user = userService.getUser(1L);
	// 	Assertions.assertNotNull(user);
	// 	System.out.println("User retrieved successfully");

	// }

}
