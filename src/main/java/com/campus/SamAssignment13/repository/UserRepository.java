package com.campus.SamAssignment13.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.campus.SamAssignment13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u"
			+ " left join fetch u.accounts"
			+ " left join fetch u.address")
		Set<User> getAllUsers();
}
