package net.longfalcon.web.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * User: Sten Martinez
 * Date: 8/24/16
 * Time: 11:08 PM
 */
public class PbkdfPasswordEncoder implements PasswordEncoder {
    private static final Log _log = LogFactory.getLog(PbkdfPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return PasswordHash.createHash(rawPassword.toString());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            _log.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean match = false;
        try {
            match = PasswordHash.validatePassword(rawPassword.toString(), encodedPassword);
        } catch (Exception e) {
            _log.error(e.toString(), e);

        }
        return match;
    }
}
