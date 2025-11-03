package com.capstone.tele_ticketing_backend_1.repo;



import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    // Dr. X's Note: This is a powerful Spring Data JPA query.
    // It finds all Feedback entities by joining through the Ticket entity
    // and filtering on the 'createdBy' user, ordering by the most recent feedback.
    List<Feedback> findAllByTicket_CreatedByOrderByCreatedAtDesc(AppUser agent);
}