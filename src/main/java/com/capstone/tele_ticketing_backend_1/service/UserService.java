package com.capstone.tele_ticketing_backend_1.service;
import com.capstone.tele_ticketing_backend_1.dto.UserSummaryDto;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.ERole;
import com.capstone.tele_ticketing_backend_1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional(readOnly = true)
    public List<UserSummaryDto> getAllCustomers() {
        List<AppUser> customers = userRepo.findAllByRoles_Name(ERole.ROLE_CUSTOMER);
        return mapUsersToSummaryDto(customers);
    }

    // Dr. X's Addition: Move the engineer-fetching logic here.
    @Transactional(readOnly = true)
    public List<UserSummaryDto> getAssignableEngineers() {
        List<ERole> engineerRoles = List.of(
                ERole.ROLE_FIELD_ENGINEER,
                ERole.ROLE_L1_ENGINEER,
                ERole.ROLE_NOC_ENGINEER
        );
        List<AppUser> engineers = userRepo.findAllByRoles_NameIn(engineerRoles);
        return mapUsersToSummaryDto(engineers);
    }

    // Helper method to avoid code duplication
    private List<UserSummaryDto> mapUsersToSummaryDto(List<AppUser> users) {
        return users.stream()
                .map(user -> new UserSummaryDto(user.getId(), user.getUsername(), user.getFullName()))
                .collect(Collectors.toList());
    }

    public List<UserSummaryDto> getUnassignedEngineers() {
        List<ERole> engineerRoles = List.of(ERole.ROLE_FIELD_ENGINEER, ERole.ROLE_L1_ENGINEER, ERole.ROLE_NOC_ENGINEER);
        List<AppUser> engineers = userRepo.findByTeamIsNullAndRoles_NameIn(engineerRoles);
        return mapUsersToSummaryDto(engineers);
    }

    @Transactional(readOnly = true)
    public List<String> getCustomerCities() {
        return userRepo.findDistinctCities();
    }

}