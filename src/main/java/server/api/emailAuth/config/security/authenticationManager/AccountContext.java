package server.api.emailAuth.config.security.authenticationManager;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class AccountContext implements UserDetails, OAuth2User {

    private final Long accountId;
    private String name;
    private Map<String, Object> attributes;
    Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    // Form Login Context
    public AccountContext(Long accountId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.accountId = accountId;
        this.username = username;
        this.name = username;
        this.password = password;
        this.authorities = authorities;
    }

    // Oauth2 Login context
    public AccountContext(Long accountId, String name, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities){
        this.accountId = accountId;
        this.username = name;
        this.name = name;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
