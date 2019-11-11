package com.spendsmart.security.oauth2;

import com.spendsmart.dto.User;
import com.spendsmart.security.OAuth2AuthenticationProcessingException;
import com.spendsmart.security.UserPrincipal;
import com.spendsmart.security.oauth2.user.OAuth2UserInfo;
import com.spendsmart.security.oauth2.user.OAuth2UserInfoFactory;
import com.spendsmart.service.UserService;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public OAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = buildOAuthUserInfo(oAuth2UserRequest, oAuth2User);
        User user = userService.getUserByEmail(oAuth2UserInfo.getEmail());
        if(user.getId() != null) {
            validateUserProvider(user, oAuth2UserRequest);
            updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private void validateUserProvider(User user, OAuth2UserRequest oAuth2UserRequest) {
        if(!user.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
            throw new OAuth2AuthenticationProcessingException(
                    "Looks like you're signed up with " + user.getProvider() + " account. " +
                            "Please use your " + user.getProvider() + " account to login.");
        }
    }

    private OAuth2UserInfo buildOAuthUserInfo(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        return oAuth2UserInfo;
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .provider(oAuth2UserRequest.getClientRegistration().getRegistrationId())
                .providerId(oAuth2UserInfo.getId())
                .firstName(oAuth2UserInfo.getAttributes().get("given_name").toString())
                .lastName(oAuth2UserInfo.getAttributes().get("family_name").toString())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .build();
        return userService.addUser(user);
    }

    private void updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setFirstName(String.valueOf(oAuth2UserInfo.getAttributes().get("given_name")));
        user.setLastName(String.valueOf(oAuth2UserInfo.getAttributes().get("family_name")));
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        userService.addUser(user);
    }
}
