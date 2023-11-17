package jaringobi.acceptance;

import io.restassured.RestAssured;
import jaringobi.domain.user.User;
import jaringobi.domain.user.UserRepository;
import jaringobi.jwt.TokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiTest{

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    protected String accessToken;

    @BeforeEach
    void setUp() {
        if(RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleaner.afterPropertiesSet();
        }
        databaseCleaner.execute();

        // setup Authentication
        User savedUser = getSavedUser();
        accessToken = tokenProvider.issueAccessToken(savedUser.getId());
    }

    private User getSavedUser() {
        User savedUser = userRepository.save(User.builder()
                .username("testuser123")
                .password("testuser123!")
                .build());
        return savedUser;
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.execute();
    }
}
