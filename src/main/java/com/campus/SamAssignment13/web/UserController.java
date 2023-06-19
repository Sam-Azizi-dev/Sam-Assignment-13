package com.campus.SamAssignment13.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campus.SamAssignment13.domain.Account;
import com.campus.SamAssignment13.domain.User;
import com.campus.SamAssignment13.services.AccountService;
import com.campus.SamAssignment13.services.UserService;

import antlr.collections.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	AccountService accountService;

//////////////////////////////////////////// Create New User Endpoints
	@GetMapping("/register")
	public String CreateNewUser(ModelMap model) {
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateNewUser(User user) {
		userService.registerNewUser(user);
		return "redirect:/register";
	}

///////////////////////////////////////////// show List Of Users	
	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.getAllUsers();
		model.put("users", users);
		return "users";
	}

//////////////////////////////// Show User Info And Update And Delete Specific User
	@GetMapping("user/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findoneUserById(userId);
		model.put("user", user);
		return "user";
	}

	@PostMapping("/user/{userId}")
	public String postOneUser(User user) {
		userService.updateUserInfo(user);
		return "redirect:/user/" + user.getUserId();
	}

	@PostMapping("/user/{userId}/delete")
	public String deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return "redirect:/users";

	}

}
