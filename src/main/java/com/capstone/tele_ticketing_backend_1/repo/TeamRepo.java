package com.capstone.tele_ticketing_backend_1.repo;



import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {

    // Finds a team based on the AppUser object who is the team lead.
    Optional<Team> findByTeamLead(AppUser teamLead);
}
