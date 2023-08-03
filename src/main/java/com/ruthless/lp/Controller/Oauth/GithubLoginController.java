/*
package com.ruthless.lp.Controller.Oauth;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class GithubLoginController {

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.redirect-uri}")
    private String redirectUri;

    @GetMapping("/api/oauth/login")
    @ResponseBody
    public String initiateOAuthLogin() {
        // You can choose your OAuth provider and construct the authorization URL accordingly.
        // For example, with Google OAuth:
        String authUrl = "https://accounts.google.com/o/oauth2/auth" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=profile email" +
                "&state=random_state"; // Optional: You can add a state parameter for CSRF protection.

        return "{\"redirectUrl\":\"" + authUrl + "\"}";
    }
}
*/
