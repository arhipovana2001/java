import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.*;

public class Main {
    static String[] VALUTES = {"AUD", "AZN", "GBP", "AMD", "BYN", "BGN", "BRL", "HUF", "HKD", "DKK", "USD", "EUR",
            "INR", "KZT", "CAD", "KGS", "CNY", "MDL", "NOK", "PLN", "RON", "XDR", "SGD", "TJS", "TRY", "TMT",
            "UZS", "UAH", "CZK", "SEK", "CHF", "ZAR", "KRW", "JPY"};

    public static void main(String[] args) throws UnirestException, IOException {
        while (true) {
            Scanner scan = new Scanner(System.in);
            String filepath = "C:\\Users\\Redmi\\IdeaProjects\\exersizes1\\parsingXML\\src\\main\\java\\valutes.xml";
            System.out.println("Введите дату в формате dd/mm/yyyy: ");
            System.out.println("Введите ISO-код валюты: ");
            System.out.println("Данные должны быть введен через пробел");
            System.out.println("Для выхода из программы введите \"exit\"");
            String date = scan.nextLine();
            String[] values = date.split(" ");
            if (values.length == 2) {
                if (isValidDate(values[0]) && isValidCharCode(values[1].toUpperCase(Locale.ROOT))) {
                    String response = Unirest.get("https://www.cbr.ru/scripts/XML_daily.asp?date_req={date}").routeParam("date", values[0]).asString().getBody();
                    response = response.replace("windows-1251", "utf-8");

                    Handler writer = new Handler();
                    writer.fileWrite(filepath, response);

                    DOMxmlReader reader = new DOMxmlReader();
                    List<Valute> valutes = reader.readFile(filepath);

                    Valute.findValute(valutes, values[1]);


                } else if (!isValidDate(values[0])) {
                    System.out.println("ВВЕДЕН НЕКОРРЕКТНЫЙ ФОРМАТ ДАТЫ");
                    System.out.println("Дату необходимо ввести в формате dd/mm/yyyy");
                    System.out.println("Например: 12/01/2020");
                } else if (!isValidCharCode(values[1])) {
                    System.out.println("ВВЕДЕН НЕКОРРЕКТНЫЙ ISO-КОД ВАЛЮТЫ");
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

    public static boolean isValidDate(String inputValue) {
        Calendar cal = new GregorianCalendar();
        cal.setLenient(false);
        cal.clear();
        try {
            int d = Integer.parseInt(inputValue.substring(0, 2));
            int m = Integer.parseInt(inputValue.substring(3, 5));
            int y = Integer.parseInt(inputValue.substring(6, 10));
            if (y > 2021) {
                return false;
            }
            cal.set(y, m - 1, d);
            Date dt = cal.getTime();
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        } catch (IllegalArgumentException iae) {
            return false;
        } catch (StringIndexOutOfBoundsException sie) {
            return false;
        }
    }

    public static Boolean isValidCharCode(String charCode) {
        for (String valute : VALUTES) {
            if (valute.equals(charCode)) {
                return true;
            }
        }
        return false;
    }


}
