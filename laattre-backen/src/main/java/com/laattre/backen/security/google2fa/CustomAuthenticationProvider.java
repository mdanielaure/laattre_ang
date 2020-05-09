package com.laattre.backen.security.google2fa;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.laattre.backen.persistence.dao.UserRepository;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.web.error.CustomBadCredentialsException;
import com.laattre.backen.web.error.DisabledException;

//@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userRepository.findByEmail(auth.getName());
        if ((user == null)) {
            throw new CustomBadCredentialsException("Invalid username or password");
        }
        if (!user.isEnabled()) {
        	throw new DisabledException("User is disabled");
        }
        // to verify verification code
        if (user.isUsing2FA()) {
            final String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
            final Totp totp = new Totp(user.getSecret());
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw new BadCredentialsException("Invalid verfication code");
            }

        }
        
        final Authentication result;
        try {
        	result = super.authenticate(auth);
		} catch (BadCredentialsException e) {
			throw new CustomBadCredentialsException("INVALID_CREDENTIALS", e);
		}
        
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
