import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws UnirestException, IOException, ClassNotFoundException, SQLException {
        while (true) {
            Handler handler = new Handler();
            DOMxmlReader reader = new DOMxmlReader();
            DataBase db = new DataBase();
            Scanner scan = new Scanner(System.in);
            List<String> VALUTES = handler.getValutes();

            String filepath = "C:\\Users\\Redmi\\IdeaProjects\\exersizes1\\parsingXML\\src\\main\\java\\valutes.xml";
            System.out.println("Введите дату в формате dd/mm/yyyy: ");
            System.out.println("Введите ISO-код валюты: ");
            System.out.println("Данные должны быть введены через пробел");
            System.out.println("Для выхода из программы введите \"exit\"");
            String date = scan.nextLine();
            String[] values = date.split(" ");
            String strDate = values[0];
            String command = values[1];
            //System.out.println("strDate" + strDate);
            //System.out.println("command" + command);

            if (values.length == 2) {
                if (handler.isValidDate(values[0]) && handler.isValidCharCode(values[1].toUpperCase(Locale.ROOT), VALUTES)) {
                    if (values[1].toUpperCase(Locale.ROOT).equals("UPDATE")) {
                        updateDB(values[0]);
                    } else {
                        ArrayList<Valute> result = null;
                        result = db.selectDB(values[1].toUpperCase(Locale.ROOT), handler.reformatDate(values[0]));
                        if (result.size() != 0) {
                            System.out.println(result.get(0));
                        } else {
                            String response = Unirest.get("https://www.cbr.ru/scripts/XML_daily.asp?date_req={date}").routeParam("date", values[0]).asString().getBody();
                            response = response.replace("windows-1251", "utf-8");

                            handler.fileWrite(filepath, response);
                            List<Valute> valutes = reader.readFile(filepath);
                            Valute.findValute(valutes, values[1]);
                        }
                    }

                } else if (!handler.isValidDate(values[0])) {
                    System.out.println("ВВЕДЕН НЕКОРРЕКТНЫЙ ФОРМАТ ДАТЫ");
                    System.out.println("Дату необходимо ввести в формате dd/mm/yyyy");
                    System.out.println("Например: 12/01/2020");
                } else if (!handler.isValidCharCode(values[1], VALUTES)) {
                    //System.out.println("values[1]" + values[1]);
                    if (values[1].toUpperCase(Locale.ROOT).equals("UPDATE")) {
                        updateDB(strDate);
                    }else {
                        System.out.println("ВВЕДЕН НЕКОРРЕКТНЫЙ ISO-КОД ВАЛЮТЫ");
                    }

                }
            } else if (values.length == 1) {
                if (values[0].toLowerCase(Locale.ROOT).equals("exit")) {
                    break;
                } else {
                    System.out.println("КОМАНДА ВВЕДЕНА НЕВЕРНО");
                }

            }

        }
    }

    public static void updateDB(String date) throws IOException, SQLException, UnirestException {
        Handler handler = new Handler();
        DOMxmlReader reader = new DOMxmlReader();
        DataBase db = new DataBase();
        //System.out.println("-------I'M HERE--------");
        String filepath = "C:\\Users\\Redmi\\IdeaProjects\\java\\SQLproject\\src\\main\\java\\valutes.xml";
        String response = Unirest.get("https://www.cbr.ru/scripts/XML_daily.asp?date_req={date}").routeParam("date", date).asString().getBody();
        response = response.replace("windows-1251", "utf-8");
        handler.fileWrite(filepath, response);
        List<Valute> valutes = reader.readFile(filepath);
        db.insertDB(valutes, handler.reformatDate(date));

    }

}
