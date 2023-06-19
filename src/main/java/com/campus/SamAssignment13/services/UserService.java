package com.campus.SamAssignment13.services;

import java.util.Optional;
import java.util.Set;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.SamAssignment13.domain.Address;
import com.campus.SamAssignment13.domain.User;
import com.campus.SamAssignment13.repository.AccountRepository;
import com.campus.SamAssignment13.repository.AddressRepository;
import com.campus.SamAssignment13.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;
	
	public Set<User> getAllUsers() {
		return userRepo.getAllUsers();
	}

	public User findoneUserById(Long userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	}

	public User registerNewUser(User user) {
		return userRepo.save(user);
	}

	public void deleteUser(Long userId) {
		 Optional<User> optionalUser = userRepo.findById(userId);
		    optionalUser.ifPresent(user -> {
		        Address address = user.getAddress();
		        if (address != null) {
		            user.setAddress(null);
		            addressRepo.deleteById(address.getUserId());
		        }
		        userRepo.deleteById(userId);
		    });
		
	}
	
	public User updateUserInfo(User user) {
		  User existingUser = userRepo.findById(user.getUserId()).orElse(null);
	        if (existingUser != null) {
	            existingUser.setUsername(user.getUsername());
	            existingUser.setPassword(user.getPassword());
	            existingUser.setName(user.getName());

	            if (user.getAddress() != null) {
	                Address existingAddress = existingUser.getAddress();
	                if (existingAddress != null) {
	                    existingAddress.setAddressLine1(user.getAddress().getAddressLine1());
	                    existingAddress.setAddressLine2(user.getAddress().getAddressLine2());
	                    existingAddress.setCity(user.getAddress().getCity());
	                    existingAddress.setRegion(user.getAddress().getRegion());
	                    existingAddress.setCountry(user.getAddress().getCountry());
	                    existingAddress.setZipCode(user.getAddress().getZipCode());
	                } else {
	                    Address newAddress = new Address();
	                    newAddress.setUser(existingUser);
	                    newAddress.setAddressLine1(user.getAddress().getAddressLine1());
	                    newAddress.setAddressLine2(user.getAddress().getAddressLine2());
	                    newAddress.setCity(user.getAddress().getCity());
	                    newAddress.setRegion(user.getAddress().getRegion());
	                    newAddress.setCountry(user.getAddress().getCountry());
	                    newAddress.setZipCode(user.getAddress().getZipCode());
	                    existingUser.setAddress(newAddress);
	                    addressRepo.save(newAddress);
	                }
	            }

	            return userRepo.save(existingUser);
	        }
	        return null;
	    }
	

}
