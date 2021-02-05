package br.com.zup.freemarket.token;

public class TokenResponse {

    private String token;
    private String authenticationType;

    public TokenResponse(String token, String authenticationType) {
        this.token = token;
        this.authenticationType = authenticationType;
    }

    public String getToken() {
        return token;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }
}
