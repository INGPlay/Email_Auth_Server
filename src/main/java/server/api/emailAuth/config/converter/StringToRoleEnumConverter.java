package server.api.emailAuth.config.converter;

import server.api.emailAuth.account.domain.subType.RoleEnum;
import server.api.emailAuth.common.domain.subType.EnumBase;
import org.springframework.core.convert.converter.Converter;

public class StringToRoleEnumConverter implements Converter<String, RoleEnum> {
    @Override
    public RoleEnum convert(String s) {
        return EnumBase.getEnumFromString(RoleEnum.class, s);
    }
}
