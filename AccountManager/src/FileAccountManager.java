import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileAccountManager implements AccountManager {

    List<Account> accounts = new ArrayList<Account>();
    FileService file = FileService.getInstance();
    FailedLoginCounter counter = FailedLoginCounter.getInstance();




    public void register(Account account) throws IOException, AccountAlreadyExistsException {
        /*
         * Метод проверяет по полю email, что данный аккаунт в базе
         * отсутствует, и создает новую запись, в противном случае
         * выбрасывает ошибку AccountAlreadyExistsException
         */
        accounts = file.csvReader();
        int toUpdate = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (Objects.equals(accounts.get(i).getEmail(), account.getEmail())) {
                // найдено значение с таким же email
                toUpdate = i;
            }
        }

        if (toUpdate == -1) {
            accounts.add(account);
            file.csvWriter(accounts);
        } else {
            throw new AccountAlreadyExistsException("в базе есть запись с такой же электронной почтой");
        }

    }

    public Account login(String email, String password) throws IOException, AccountBlockedException, WrongCredentialsException {
        /*
         * Метод возвращает Account, если для email+пароль есть
         * подходящая запись в базе и аккаунт не заблокирован.
         * Если неверно введены email и/или пароль - выбрасывается
         * исключение WrongCredentialsException.
         * Если email и пароль верны, но аккаунт заблокирован -
         * выбрасывается исключение AccountBlockedException.
         * Если для конкретного пользователя больше 5 неудачных
         * попыток авторизоваться, то аккаунт блокируется.*/
        accounts = file.csvReader();
        int index = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (Objects.equals(accounts.get(i).getEmail(), email)) {
                // найдено значение с таким же email
                index = i;

            }
        }

        if (index == -1) {
            // нет записи с таким емайлом
            throw new WrongCredentialsException("неверно введены email и/или пароль");
        } else {
            // есть запись с таким емайлом
            if (Objects.equals(accounts.get(index).getPassword(), password) && !accounts.get(index).getBlocked() &&
                    accounts.get(index).getCount() < 5) {
                // пароль верный, аккаунт не заблокирован
                System.out.println("Метод логин сработал верно:" + accounts.get(index));
                return accounts.get(index);
            } else if (Objects.equals(accounts.get(index).getPassword(), password) && accounts.get(index).getCount() >= 5) {
                // email и пароль верны, но аккаунт заблокирован
                throw new AccountBlockedException("аккаунт заблокирован");
            } else if (!Objects.equals(accounts.get(index).getPassword(), password)) {
                // неверный пароль
                counter.plus(accounts.get(index));
                if (accounts.get(index).getCount() >= 5) {
                    accounts.get(index).setBlocked();
                }
                file.csvWriter(accounts);
                throw new WrongCredentialsException("1неверно введены email и/или пароль");
            }
        }


        return accounts.get(index);
    }

    public void removeAccount(String email, String password) throws IOException, WrongCredentialsException {
        /*
         * Метод удаляет пользователя из базы, если логин и пароль
         * введены верно. В противном случае выбрасывает
         * ошибку WrongCredentialsException
         */
        accounts = file.csvReader();
        int index = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (Objects.equals(accounts.get(i).getEmail(), email)) {
                // найдено значение с таким же email
                index = i;

            }
        }

        if (index == -1) {
            // нет записи с таким емайлом
            throw new WrongCredentialsException("неверно введены email и/или пароль");
        } else {
            // есть запись с таким емайлом
            if (Objects.equals(accounts.get(index).getPassword(), password)) {
                // пароль верный, аккаунт не заблокирован
                System.out.println("Метод remove сработал верно:" + accounts.get(index));
                accounts.remove(index);
                file.csvWriter(accounts);
            } else {
                throw new WrongCredentialsException("неверно введены email и/или пароль");
            }
        }
    }
}

// подключение БД после того, как скачали
// cd
// cd .\my_sql...
// cd bin
// .\mysql --user=root
//