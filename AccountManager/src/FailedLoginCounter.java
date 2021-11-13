import java.util.*;

public class FailedLoginCounter {

    // добавить в класс приватное статическое поле, содержащее одиночный объект:
    private static FailedLoginCounter instance;


    // Сделать конструктор класса (конструктор по-умолчанию) приватным
    private FailedLoginCounter() {
    }

    // Объявить статический создающий метод, который будет использоваться для получения одиночки:
    public static FailedLoginCounter getInstance() { // #3
        if (instance == null) {        //если объект еще не создан
            instance = new FailedLoginCounter();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }

    public void registration(Account a) {

    }

    public static void delete(Account a) {

    }

    public void plus(Account a) {
        System.out.println("method plus before: " + a);
        a.countPlus();
        System.out.println("method plus after: " + a);
    }
}
