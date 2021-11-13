import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileService {
    // добавить в класс приватное статическое поле, содержащее одиночный объект:
    private static FileService instance;

    // Сделать конструктор класса (конструктор по-умолчанию) приватным
    private FileService() {
    }

    // Объявить статический создающий метод, который будет использоваться для получения одиночки:
    public static FileService getInstance() { // #3
        if (instance == null) {        //если объект еще не создан
            instance = new FileService();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }

    public List<Account> csvReader() throws IOException {
        // открываем файл
        List<Account> accounts = new ArrayList<Account>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\Redmi\\IdeaProjects\\exersizes1\\src\\base.csv"));

            // считываем построчно
            String line = null;
            Scanner scanner = null;
            int index = 0;


            while ((line = reader.readLine()) != null) {
                Account account = new Account();
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    if (index == 0)
                        account.setFirstName(data);
                    else if (index == 1)
                        account.setMiddleName(data);
                    else if (index == 2)
                        account.setLastName(data);
                    else if (index == 3)
                        account.setBirthDate(data);
                    else if (index == 4)
                        account.setEmail(data);
                    else if (index == 5)
                        account.setPassword(data);
                    else if (index == 6)
                        account.setBlocked();
                    else if (index == 7)
                        account.setCount(0);
                        //account.setCount(Integer.valueOf(data));
                    else
                        System.out.println("Некорректные данные:" + data);
                    index++;
                }
                index = 0;
                accounts.add(account);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader !=null){
                reader.close();
            }

        }


        //закрываем наш ридер
        return accounts;
    }

    public void csvWriter(List<Account> accounts) throws IOException {

        String path = "C:\\Users\\Redmi\\IdeaProjects\\exersizes1\\src";

        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "\\base.csv"));

        for (Account account : accounts) {
            writer.write(String.valueOf(account));
        }
        writer.close();
    }


}