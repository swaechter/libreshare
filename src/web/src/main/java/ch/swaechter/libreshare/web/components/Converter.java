package ch.swaechter.libreshare.web.components;

import ch.swaechter.libreshare.web.components.account.dto.ReadAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.UpdateAccountDto;
import ch.swaechter.libreshare.web.components.account.table.Account;
import io.micronaut.context.annotation.Context;

import java.util.ArrayList;
import java.util.List;

@Context
public class Converter {

    public List<ReadAccountDto> accountsToReadAccountDtos(List<Account> accountList) {
        return new ArrayList<>();
    }

    public ReadAccountDto accountToReadAccountDto(Account account) {
        return null;
    }

    public Account updateAccountDtoToAccount(UpdateAccountDto updateAccountDto) {
        return null;
    }
}
