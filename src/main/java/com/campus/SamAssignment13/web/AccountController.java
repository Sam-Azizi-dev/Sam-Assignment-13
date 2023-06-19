package com.campus.SamAssignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.campus.SamAssignment13.domain.Account;
import com.campus.SamAssignment13.domain.User;
import com.campus.SamAssignment13.services.AccountService;
import com.campus.SamAssignment13.services.UserService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;

////////////////////////////Create New And Update Bank Account 

	@PostMapping("/user/{userId}/accounts")
	public String postCreateNewBankAccount(@PathVariable Long userId) {
		Account account = accountService.createnewBankAccount(userId);
		Long accountId = account.getAccountId();
		return "redirect:/user/" + userId + "/accounts/" + accountId;
	}

	@GetMapping("/user/{userId}/accounts/{accountId}")
	public String EditAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findoneUserById(userId);
		model.addAttribute("user", user);
		Account newAccount = accountService.findAccountById(accountId);
		model.addAttribute("account", newAccount);
		return "account";
	}

	@PostMapping("/user/{userId}/accounts/{accountId}")
	public String postEditAccount(@PathVariable Long userId, @PathVariable Long accountId) {
		accountService.SaveBankAccount(userId,accountId);
		return "redirect:/user/" + userId + "/accounts/" + accountId;
	}
}
