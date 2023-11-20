package jaringobi.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jaringobi.jwt.TokenProvider.TokenType;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

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

    @Test
    @DisplayName("요청 헤더의 Authorization 필드에 있는 값을 파싱한다.")
    void parseAuthorizationFieldValue() {
        String accessToken = tokenProvider.issueAccessToken(1L);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer " + accessToken);

        String token = tokenProvider.parseTokenFromHeader(mockHttpServletRequest);

        assertThat(token).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("정상적인 토큰의 경우 유효성을 검사 시 True 반환")
    void returnTrueValidToken() {
        String invalidToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX3NlcSI6MSwidG9rZW5fdHlwZSI6IkFDQ0VTUyIsImlhdCI6MTY5OTk1NDUwMywiZXhwIjoxNjk5OTU1MTAzfQ.wVClKGLKceyTZwk8vncU-Ml9F0-UVDLoq7sLajV13G5UIiEmgYqTWIJHLbxN1uJlVQf50-5YTKGJzGZQCAg0Mg";
        assertThat(tokenProvider.isValidToken(invalidToken)).isFalse();
    }

    @Test
    @DisplayName("정상적인 토큰의 경우 유효성을 검사 시 False 반환")
    void throwExceptionNotInvalidToken() {
        String accessToken = tokenProvider.issueAccessToken(1L);
        assertThat(tokenProvider.isValidToken(accessToken)).isTrue();
    }

    @Test
    @DisplayName("유효한 토큰의 경우 예외를 던지지 않는다.")
    void notThrowExceptionWhenValidToken() {
        String accessToken = tokenProvider.issueAccessToken(1L);
        assertThatCode(() -> tokenProvider.isValidToken(accessToken))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Token 으로부터 'user_seq' Claim 을 가져온다.")
    void getUserSeqFromToken() {
        String accessToken = tokenProvider.issueAccessToken(1L);
        Long userId = tokenProvider.parseUserId(accessToken);
        assertThat(userId).isEqualTo(1L);
    }
}