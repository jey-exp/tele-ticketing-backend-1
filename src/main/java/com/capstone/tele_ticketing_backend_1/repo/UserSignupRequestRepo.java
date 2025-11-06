package com.capstone.tele_ticketing_backend_1.repo;

import com.capstone.tele_ticketing_backend_1.entities.UserSignupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSignupRequestRepo extends JpaRepository<UserSignupRequest, Long> {
}
