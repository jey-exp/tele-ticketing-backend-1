package com.capstone.tele_ticketing_backend_1.repo;

import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Boolean existsByUsername(String username);

    List<AppUser> findAllByRoles_NameIn(List<ERole> roles);

    List<AppUser> findAllByRoles_Name(ERole roleName);

    List<AppUser> findByTeamIsNullAndRoles_NameIn(List<ERole> roles);
    @Query("SELECT DISTINCT u.city FROM AppUser u WHERE u.city IS NOT NULL AND u.city != '' ORDER BY u.city")
    List<String> findDistinctCities();
}
