package com.campus.SamAssignment13.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.campus.SamAssignment13.domain.Account;
import com.campus.SamAssignment13.domain.User;
import com.campus.SamAssignment13.repository.AccountRepository;
import com.campus.SamAssignment13.repository.UserRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private UserRepository userRepository;

	public Account findAccountById(Long accountId) {
		return accountRepo.findById(accountId).orElseThrow(null);
	}
	public Long findLastAccountId(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		List<Account> accounts = user.getAccounts();
		if (accounts.isEmpty()) {
			return 0L;
		} else {
			return accounts.get(accounts.size() - 1).getAccountId();
		}
	}

	public Account updateBankAccount(Long userId, Long accountId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new IllegalArgumentException("User not found");
		}

		Account account = user.getAccounts().stream().filter(a -> a.getAccountId().equals(accountId)).findFirst()
				.orElse(null);

		if (account == null) {
			throw new IllegalArgumentException("Account not found");
		}

		account.setAccountName("Account#" + (user.getAccounts().size() + 1));
		userRepository.save(user); // Save the modified User entity

		return account;
	}

	public Account createnewBankAccount(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		Account account = new Account();
		account.setAccountName("Account#" + (user.getAccounts().size() + 1));
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		userRepository.save(user);
		return account;

	}

	public void SaveBankAccount(Long userId,Long accountId) {
		Account account = accountRepo.findById(accountId).orElseThrow(null);
		accountRepo.save(account);
		User user = userRepository.findById(userId).orElseThrow(null);
		userRepository.save(user);
	}
}

//@Service
//public class AccountService {
//
//	@Autowired
//	private AccountRepository accountRepo;
//	@Autowired
//	private UserRepository userRepo;
//
//	public void createNewBankAccount(Long userId) {
//		User user = userRepo.findById(userId)
//				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//		Account bankAccount = new Account();
//		if (user.getAccounts() == null) {
//			bankAccount.setAccountName("Account#1");
//		} else {
//			bankAccount.setAccountName("Account#" + (user.getAccounts().size() + 1));
//		}
//		bankAccount.getUsers().add(user);
//		user.getAccounts().add(bankAccount);
//		accountRepo.save(bankAccount);
//		userRepo.save(user);
//	}
//	
//}
