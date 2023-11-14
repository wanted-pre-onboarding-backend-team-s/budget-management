package jaringobi.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jaringobi.jwt.TokenProvider.TokenType;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JWT 토큰 생성기 테스트")
class TokenProviderTest {

    // 초 단위
    public static final int TOKEN_LIFE_TIME = 2;

    TokenProperty tokenProperty;
    TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProperty = new TokenProperty();
        tokenProperty.setTokenLifeTime(TOKEN_LIFE_TIME);
        tokenProvider = new TokenProvider(tokenProperty);
    }

    @Test
    @DisplayName("TokenProvider 를 이용해 AccessToken 을 발행한다.")
    void issueAccessToken() {
        // When
        String accessToken = tokenProvider.issueAccessToken(1L);

        // Then
        DecodedJWT jwt = JWT.decode(accessToken);
        Long userSeq = jwt.getClaim("user_seq").asLong();
        String type = jwt.getClaim("token_type").asString();

        assertThat(userSeq).isEqualTo(1L);
        assertThat(TokenType.of(type)).isEqualTo(TokenType.ACCESS);
    }

    @Test
    @DisplayName("TokenProvider 를 이용해 RefreshToken 을 발행한다.")
    void issueRefreshToken() {
        // When
        String refreshToken = tokenProvider.issueRefreshToken(1L);

        // Then
        DecodedJWT jwt = JWT.decode(refreshToken);
        Long userSeq = jwt.getClaim("user_seq").asLong();
        String type = jwt.getClaim("token_type").asString();

        assertThat(userSeq).isEqualTo(1L);
        assertThat(TokenType.of(type)).isEqualTo(TokenType.REFRESH);
    }

    @Test
    @DisplayName("발급된 AccessToken 은 RefreshToken 보다 만료시간이 더 짦다.")
    void accessTokenHasShorterExpirationTime() {
        // When
        String refreshToken = tokenProvider.issueRefreshToken(1L);
        String accessToken = tokenProvider.issueAccessToken(1L);
        DecodedJWT decodeAccess = JWT.decode(accessToken);
        DecodedJWT decodeRefresh = JWT.decode(refreshToken);

        Instant accessTokenInstant = decodeAccess.getClaim("exp").asInstant();
        Instant refreshTokenInstant = decodeRefresh.getClaim("exp").asInstant();

        // Then
        assertThat(accessTokenInstant).isBefore(refreshTokenInstant);
    }

    @Test
    @DisplayName("TokenLifeTime 시간이 지나기 전까지는 발행한 AccessToken 은 만료되지 않는다.")
    void accessTokenNowExpiresWithInTokenLifeTime() throws InterruptedException {
        // When
        String accessToken = tokenProvider.issueAccessToken(1L);

        // 1초후
        Thread.sleep(100);

        DecodedJWT decodedJWT = JWT.decode(accessToken);
        Date expiresAt = decodedJWT.getExpiresAt();

        assertThat(expiresAt.before(new Date())).isFalse();
    }

    @Test
    @DisplayName("TokenLifeTime 이후 발행한 AccessToken 은 만료된다.")
    void accessTokenExpiresAfterTokenLifeTime() throws InterruptedException {
        // When
        String accessToken = tokenProvider.issueAccessToken(1L);

        // 1초후
        Thread.sleep(3000);

        DecodedJWT decodedJWT = JWT.decode(accessToken);
        Date expiresAt = decodedJWT.getExpiresAt();

        assertThat(expiresAt.before(new Date())).isTrue();
    }
}