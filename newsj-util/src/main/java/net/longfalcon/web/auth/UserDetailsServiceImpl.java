package net.longfalcon.web.auth;

import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Sten Martinez
 * Date: 8/24/16
 * Time: 10:46 PM
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        String passwordEnc = user.getPassword();
        boolean enabled = UserService.ROLE_DISABLED != user.getRole();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(2);
        if (user.getRole() == UserService.ROLE_ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getRole() == UserService.ROLE_USER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getRole() == UserService.ROLE_GUEST) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }

        NewsJUserDetails userDetails = new NewsJUserDetails(username, passwordEnc, user.getId(), enabled, authorities);
        return userDetails;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
