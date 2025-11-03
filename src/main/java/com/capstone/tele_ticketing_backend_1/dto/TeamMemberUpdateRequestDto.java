package com.capstone.tele_ticketing_backend_1.dto;

import lombok.Data;
import java.util.Set;

@Data
public class TeamMemberUpdateRequestDto {
    private Set<Long> userIdsToAdd;
    private Set<Long> userIdsToRemove;
}
