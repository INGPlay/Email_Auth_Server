package server.api.emailAuth.account.service;

import server.api.emailAuth.account.domain.RegisterAccountDTO;
import server.api.emailAuth.account.domain.subType.OAuthTypeEnum;
import server.api.emailAuth.account.repository.AccountRepository;
import server.api.emailAuth.account.repository.querydsl.QueryAccountRepository;
import server.api.emailAuth.common.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;
    private final QueryAccountRepository queryAccountRepository;

    public Boolean isInDb(String accountName){
        return accountRepository.findByAccountName(accountName).isPresent();
    }

    public Long insertLocalAccount(RegisterAccountDTO registerAccountDTO){
        Account account = new Account();
        account.setAccountName(registerAccountDTO.getAccountName());
        account.setAccountPassword(passwordEncoder.encode(registerAccountDTO.getAccountPassword()));
        account.setAccountDisplayName(registerAccountDTO.getAccountDisplayName());
        account.setAccountRole(registerAccountDTO.getAccountRole());
        account.setOauthType(OAuthTypeEnum.LOCAL);

        Account savedAccount = accountRepository.save(account);

        return savedAccount.getAccountId();
    }
}
