package com.algaworks.algafood.auth.core;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (authentication.getPrincipal() instanceof AuthUser) {
            var authUser = (AuthUser) authentication.getPrincipal();

            var info = new HashMap<String, Object>();
            info.put("user_id", authUser.getUserId());
            info.put("nome_completo", authUser.getFullName());
            info.put("email", authUser.getUsername());

            var oAuth2AcceessToken = (DefaultOAuth2AccessToken) accessToken;
            oAuth2AcceessToken.setAdditionalInformation(info);

            return oAuth2AcceessToken;
        }

        return accessToken;
    }
}
