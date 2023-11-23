package server.api.emailAuth.config.converter;

import server.api.emailAuth.account.domain.subType.OAuthTypeEnum;
import server.api.emailAuth.common.domain.subType.EnumBase;
import org.springframework.core.convert.converter.Converter;

public class StringToOAuthTypeConverter implements Converter<String, OAuthTypeEnum> {
    @Override
    public OAuthTypeEnum convert(String s) {
        return EnumBase.getEnumFromString(OAuthTypeEnum.class, s);
    }
}
