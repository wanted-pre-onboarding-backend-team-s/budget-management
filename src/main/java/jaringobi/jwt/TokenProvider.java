package jaringobi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jaringobi.exception.auth.AuthenticationException;
import jaringobi.exception.auth.InvalidTokenException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String USER_SEQ = "user_seq";
    private static final String TOKEN_TYPE = "token_type";
    private static final String EXP = "exp";
    private static final String IAT = "iat";
    public static final String AUTHORIZATION = "Authorization";

    public enum TokenType {
        ACCESS,
        REFRESH;

        public final static List<TokenType> tokens = Arrays.stream(TokenType.values()).toList();

        public static TokenType of(String type) {
            return tokens.stream()
                    .filter(it -> it.name().equals(type))
                    .findFirst()
                    .orElseThrow(InvalidTokenException::new);
        }
    }

    public String parseTokenFromHeader(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(AUTHORIZATION);
        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
            throw new AuthenticationException();
        }
        return authorization.substring(7);
    }

    private final Algorithm AL;
    private final TokenProperty jwtProperties;

    public TokenProvider(TokenProperty jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.AL = Algorithm.HMAC512(jwtProperties.getSecret());
    }

    private String generate(Long userSeq, TokenType tokenType) {
        long now = Instant.now().getEpochSecond();
        return JWT.create().withClaim(USER_SEQ, userSeq)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withClaim(IAT, now)
                .withClaim(EXP, now + getLifeTime(tokenType))
                .sign(AL);
    }

    public String issueAccessToken(Long userSeq) {
        return generate(userSeq, TokenType.ACCESS);
    }

    public String issueRefreshToken(Long userSeq) {
        return generate(userSeq, TokenType.REFRESH);
    }

    public boolean isExpired(String token) {
        Date expiresAt = JWT.decode(token)
                .getClaim("exp").asDate();
        return expiresAt.before(new Date());
    }

    public boolean isValidToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(AL)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public Long parseUserId(String token) {
        return JWT.decode(token).getClaim(USER_SEQ).asLong();
    }

    private long getLifeTime(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS -> this.jwtProperties.getTokenLifeTime();
            default -> this.jwtProperties.getTokenRefreshTime();
        };
    }
}
