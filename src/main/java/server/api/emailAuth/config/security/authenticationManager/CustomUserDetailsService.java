package server.api.emailAuth.config.security.authenticationManager;

import server.api.emailAuth.account.domain.subType.OAuthTypeEnum;
import server.api.emailAuth.account.repository.querydsl.QueryAccountRepository;
import server.api.emailAuth.common.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final HttpServletRequest httpsServletRequest;
    private final QueryAccountRepository queryAccountRepository;

    // Form 로그인
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {

        log.info("accountName : {}", accountName);
        log.info("login ip : {}", httpsServletRequest.getRemoteAddr());

        Account account = queryAccountRepository.selectAccountOne(accountName, OAuthTypeEnum.LOCAL).orElseThrow(
                () -> new UsernameNotFoundException("UsernameNotFoundException")
        );

        List<GrantedAuthority> roles = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getAccountRole().name());
        roles.add(authority);

        return new AccountContext(
                account.getAccountId(), account.getAccountName(), account.getAccountPassword(), roles
        );
    }
}
