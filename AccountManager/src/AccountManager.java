import java.io.IOException;

// Интерфейс управления аккаунтами, для базы используется текстовый файл в формате CSV
public interface AccountManager {

    void register(Account account) throws IOException, AccountAlreadyExistsException;

    Account login(String email, String password) throws IOException, AccountBlockedException, WrongCredentialsException;

    void removeAccount(String email, String password) throws IOException, WrongCredentialsException;
}
