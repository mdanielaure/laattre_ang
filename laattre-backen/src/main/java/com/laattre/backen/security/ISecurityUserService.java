package com.laattre.backen.security;

import com.laattre.backen.persistence.model.User;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);
    
    /**
     * Gets the current logged in User.
     *
     * @return the logged in User information
     */
    User getCurrentUser();


}
