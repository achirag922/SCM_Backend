package com.scm.backend.dto;

import com.scm.backend.entities.User;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        User user
) {
}
