package br.com.seuespacounb.turing.config;

import br.com.seuespacounb.turing.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    @Value("minha-chave-super-secreta-do-projeto-turing-2026")
    private String secret;

    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", usuario.getId())
                .withSubject(usuario.getEmail())
//                .withClaim("ROLE_", usuario.getTipoUsuario().name())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
    public Optional<JWTUserData> validateToken(String token){

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build()
            );

        }catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
