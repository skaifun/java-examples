package org.example.model;

import java.time.Instant;

public record UserDTO(
    Long id,
    String username,
    String email,
    String avatar,
    String ipAddress,
    Instant creationTime
) {
}
