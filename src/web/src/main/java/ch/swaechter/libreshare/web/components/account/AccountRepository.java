package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.account.table.Account;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Manage the account access to the SQL server.
 *
 * @author Simon Wächter
 */
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface AccountRepository extends CrudRepository<Account, UUID> {

    /**
     * Find an optional account by username.
     *
     * @param username Username of the account
     * @return Optional account
     */
    Optional<Account> findByUsername(String username);
}
