package com.wanted.bobo.common.jwt;

public interface JwtProperties {

    String SECRET = "bobo";
    long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60L;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}