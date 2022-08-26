import java.io.Serializable;
import java.sql.*;
import java.util.Scanner;

public class Main implements Serializable {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Conn.Connect();
        Conn.CreateDB();

        Scanner scan = new Scanner(System.in);
        Boolean b = true;
String s;
        while (b) {
s=scan.nextLine();
            if (s == null || "".equals(s)){
                break;}
            Conn.WriteDB(s);
        }

        Conn.ReadDB();
        Conn.CloseDB();}



    public class Conn {
        public static Connection conn;
        public static Statement statmt;
        public static ResultSet resSet;

        // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
        public static void Connect() throws ClassNotFoundException, SQLException {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");

        }

        // --------Создание таблицы--------
        public static void CreateDB() throws ClassNotFoundException, SQLException {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'CRM' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Customer' text, 'Product' text, 'Quantity' INT);");
        }

        // --------Заполнение таблицы--------
        public static void WriteDB(String s) throws SQLException {
try{
                    String s1 = s.split(" ")[0];
                    String s2 =  s.split(" ")[1];
                    int i = Integer.parseInt( s.split(" ")[2]);
                    statmt.execute("INSERT INTO 'CRM' ('Customer', 'Product', 'Quantity') VALUES ('" + s1 + "', '" + s2 + "', " + i + "); ");
                } catch (Exception e) {

                }
            }


        // -------- Вывод таблицы--------
        public static void ReadDB() throws ClassNotFoundException, SQLException {
            resSet = statmt.executeQuery("SELECT * FROM CRM");

            while (resSet.next()) {
                int id = resSet.getInt("id");
                String customer = resSet.getString("Customer");
                String product = resSet.getString("Product");
                int quantity = resSet.getInt("Quantity");
//                System.out.println( "ID = " + id );
                System.out.println(customer + ":");
                System.out.print(product + " ");
                System.out.print(quantity);
                System.out.println();
            }

        }

        // --------Закрытие--------
        public static void CloseDB() throws ClassNotFoundException, SQLException {
            conn.close();
            statmt.close();
            resSet.close();
        }


}
}