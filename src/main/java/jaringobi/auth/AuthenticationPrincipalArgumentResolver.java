package jaringobi.auth;

import jakarta.servlet.http.HttpServletRequest;
import jaringobi.jwt.TokenProvider;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationService authenticationService;
    private final TokenProvider tokenProvider;

    public AuthenticationPrincipalArgumentResolver(AuthenticationService authenticationService,
            TokenProvider tokenProvider) {
        this.authenticationService = authenticationService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        String token = tokenProvider.parseTokenFromHeader(
                Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        return authenticationService.findUserByToken(token);
    }
}
