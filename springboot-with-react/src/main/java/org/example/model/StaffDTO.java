package org.example.model;

import java.time.LocalDate;

public record StaffDTO(
    Long id,
    String firstName,
    String lastName,
    Gender gender,
    String phoneNumber,
    LocalDate joinDate
) {
}
