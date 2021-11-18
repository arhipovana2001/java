import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class DataBase {

    private final static String url = "jdbc:mysql://localhost:3306/valutes";
    private final static String user = "root";
    private Connection connection;

    public DataBase() {
    }

    public void showDB() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, user, "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Valutes;");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("Id") + "\n" +
                                resultSet.getInt("NumCode") + "\n" +
                                resultSet.getString("CharCode") + "\n" +
                                resultSet.getInt("Nominal") + "\n" +
                                resultSet.getString("Name") + "\n" +
                                resultSet.getDouble("Value")
                );

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (connection != null){
            connection.close();
        }
    }

    public ArrayList<Valute> selectDB(String charCode, String date) throws SQLException {
        // возвращает список валют
        String query;
        String res = "";
        Valute val = new Valute();
        ArrayList<Valute> valutes = new ArrayList<Valute>();
        try {
            query = String.format("SELECT * FROM Valutes WHERE CharCode=\"%s\" AND Date=\"%s\";", charCode, date);
            Connection connection = DriverManager.getConnection(url, user, "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                val.setNumCode(String.valueOf(resultSet.getInt("NumCode")));
                val.setCharCode(resultSet.getString("CharCode"));
                val.setNominal(resultSet.getInt("Nominal"));
                val.setName(resultSet.getString("Name"));
                val.setValue(resultSet.getDouble("Value"));
                valutes.add(val);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (connection != null){
            connection.close();
        }
        return valutes;
    }

    public void insertDB(List<Valute> valutes, String date) throws SQLException {
        String query;

        try {
            Connection connection = DriverManager.getConnection(url, user, "password");
            Statement statement = connection.createStatement();
            //System.out.println("----------------------");
            //System.out.println(valutes);
            for (Valute valute : valutes){
                query = String.format("INSERT Valutes(NumCode, CharCode, Nominal, Name, Value, Date) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
                        valute.getNumCode(), valute.getCharCode(), String.valueOf(valute.getNominal()), valute.getName(),
                        String.valueOf(valute.getValue()), date);
                //System.out.println(query);
                statement.executeUpdate(query);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (connection != null){
            connection.close();
        }

    }
}






