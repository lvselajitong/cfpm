package com.yihong.cfpm.model;

import static org.mvel2.MVEL.evalToString;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AccessToken {

    private final String accessToken;
    
    private final String refreshToken;
    
    private final String expires;

    private final String tokenType;

    private String id;

    private String username;

    /**
     * Constructs a new access token.
     *
     * @param accessToken  the token value
     * @param tokenType    the token type
     */
    public AccessToken(String accessToken, String tokenType, String refreshToken, String expires) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expires = expires;
    }

    /**
     * Returns the token value.
     *
     * @return the token value
     */
    public String getAccessToken() {
        return accessToken;
    }
    /**
     * Returns the refresh token value.
     *
     * @return the refresh token value
     */

    public String getRefreshToken(){
    	return refreshToken;
    }
    /**
     * Returns the expires.
     *
     * @return the expires
     */
    public String getExpires() {
    	return expires;
    }
    /**
     * Returns the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Returns the id of the user the token belongs to.
     *
     * @return the id of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the user name of the user the token belongs to.
     *
     * @return the user name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the user id of the user the token belongs to.
     *
     * @param id  the id of the user
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the user name of the user the token belongs to.
     *
     * @param username  the user name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /**
     * Constructs a new access token from the given API response.
     *
     * @param response  the response to create the access token from
     *
     * @return a new access token
     */
    public static AccessToken fromCloudFoundryModel(Object response) {
        return new AccessToken(evalToString("access_token", response), evalToString("token_type", response), evalToString("refresh_token", response), evalToString("expires_in", response));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("accessToken", accessToken)
                .append("tokenType", tokenType)
                .append("refreshToken", refreshToken)
                .append("expires", expires)
                .append("id", id)
                .append("username", username).toString();
    }

}

