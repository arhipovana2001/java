import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Handler implements ValuteManager {

    static void fileWrite(String filepath, String text) throws IOException {
        File file = new File(filepath);
        FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
        try{
            writer.write(text);
        }finally {
            writer.flush();
            writer.close();
        }
    }

    public boolean isValidDate(String inputValue) {
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

    public Boolean isValidCharCode(String charCode, List<String> VALUTES) {
        for (String valute : VALUTES) {
            if (valute.equals(charCode)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getValutes() throws UnirestException, IOException {
        String response = Unirest.get("https://www.cbr.ru/scripts/XML_valFull.asp").asString().getBody();
        //System.out.println("-------RESPONSE-----------");
        //System.out.println(response);
        response = response.replace("windows-1251", "UTF-8");
        fileWrite("C:\\Users\\Redmi\\IdeaProjects\\java\\SQLproject\\src\\main\\java\\valutes.xml", response);
        List<String> valString = DOMxmlReader.readValutesFile("C:\\Users\\Redmi\\IdeaProjects\\java\\SQLproject\\src\\main\\java\\valutes.xml");
        return valString;
    }

    public String reformatDate(String oldDate){
        int d = Integer.parseInt(oldDate.substring(0, 2));
        int m = Integer.parseInt(oldDate.substring(3, 5));
        int y = Integer.parseInt(oldDate.substring(6, 10));
        return y + "-" + m + "-" + d;
    }

}
