package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.account.table.Account;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface AccountRepository extends CrudRepository<Account, UUID> {

    Optional<Account> findByUserName(String userName);
}
