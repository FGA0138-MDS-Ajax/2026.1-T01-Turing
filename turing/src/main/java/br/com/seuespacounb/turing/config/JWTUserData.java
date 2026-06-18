package br.com.seuespacounb.turing.config;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String email
) {
}