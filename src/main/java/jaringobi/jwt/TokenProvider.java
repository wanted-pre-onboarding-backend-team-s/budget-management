package jaringobi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jaringobi.exception.jwt.InvalidTokenException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String USER_SEQ = "user_seq";
    private static final String TOKEN_TYPE = "token_type";
    private static final String EXP = "exp";
    private static final String IAT = "iat";

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

    private long getLifeTime(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS -> this.jwtProperties.getTokenLifeTime();
            default -> this.jwtProperties.getTokenRefreshTime();
        };
    }
}
