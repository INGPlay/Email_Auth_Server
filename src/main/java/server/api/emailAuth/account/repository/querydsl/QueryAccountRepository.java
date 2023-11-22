package server.api.emailAuth.account.repository.querydsl;

import server.api.emailAuth.account.domain.subType.OAuthTypeEnum;
import server.api.emailAuth.common.entity.Account;
import server.api.emailAuth.common.entity.QAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryAccountRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QAccount account = QAccount.account;

    public Optional<Account> selectAccountOne(String accountName, OAuthTypeEnum oauthType){

        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(account)
                        .where(
                                account.accountName.eq(accountName),
                                account.oauthType.eq(oauthType)
                        ).fetchOne()
        );
    }
}
