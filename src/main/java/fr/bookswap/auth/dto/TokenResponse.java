package fr.bookswap.auth.dto;

public class TokenResponse {

    public String accessToken;
    public String tokenType;
    public long expiresIn;

    public TokenResponse(String accessToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}