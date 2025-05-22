package edu.mondragon.webengl.domain.user.model;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenStore {
    private final Map<String, String> tokenStore = new HashMap<>();

    public void storeToken(String email, String token) {
        tokenStore.put(token, email);
    }

    public String getEmailByToken(String token) {
        return tokenStore.get(token);
    }
}
