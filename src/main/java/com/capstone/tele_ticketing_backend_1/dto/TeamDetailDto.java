package com.capstone.tele_ticketing_backend_1.dto;



import lombok.Data;
import java.util.List;

@Data
public class TeamDetailDto {
    private Long id;
    private String name;
    private List<UserSummaryDto> members;
}
