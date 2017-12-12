import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {

    private static ServerSocket listener = null;
    private static Socket socket = null;
    private static Gson gson = null;
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static PreparedStatement preparedStatement = null;
    private static Boolean isInDb = false;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            listener = new ServerSocket(8888);
            gson = new Gson();

            while (true) {
                socket = listener.accept();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    JsonReader reader = new JsonReader(in);
                    JsonWriter writer = new JsonWriter(out);
                    String fromJson = null;

                    try{
                        if (socket.isConnected()){
                            conn = DriverManager.getConnection("jdbc:mysql://localhost/mobilki", "root", "");
                        }

                        fromJson = gson.fromJson(reader, String.class);
                        String[] data = fromJson.split(":");

                        String selectSQL = "SELECT k.email, k.haslo FROM klienci k WHERE k.email = ? AND k.haslo = ?";
                        String updateSQL = "INSERT INTO `kinomat`.`klienci` (`id_klienta`, `email`, `haslo`, `pesel`, `nazwisko`, `imie`) VALUES ('1', 'emailK', 'passwordK', '1234', 'damian', 'nameK')";
                        if (!conn.isClosed()){
                            preparedStatement = conn.prepareStatement(selectSQL);
                            preparedStatement.setString(1, data[0]);
                            preparedStatement.setString(2, data[1]);

                            rs = preparedStatement.executeQuery();

                            if (rs.next())
                                isInDb = true;

                            gson.toJson(isInDb, Boolean.class, writer);
                            writer.flush();
                            preparedStatement.close();

                            String sql = "SELECT * FROM miejsca LIMIT 1";
                            stmt = conn.createStatement();
                            rs = stmt.executeQuery(sql);
                            ArrayList<Integer> list = new ArrayList<>();
                            if (rs.next()){
                               for (int i = 3; i < 53; ++i){
                                   list.add(rs.getInt(i));
                               }
                            }
                            gson.toJson(list, ArrayList.class, writer);
                            writer.flush();
                            rs.close();
                            stmt.close();



                            String sql1 = "SELECT filmy.tytul, seanse.data FROM filmy,seanse, klienci, rezerwacje WHERE " +
                                    "klienci.id_klienta=rezerwacje.id_klienta AND seanse.id_seans=rezerwacje.id_seans " +
                                    "AND seanse.id_film=filmy.id_film AND klienci.email = ?";

                            preparedStatement = conn.prepareStatement(sql1);

                            preparedStatement.setString(1, data[0]);

                            rs =preparedStatement.executeQuery();

                            ArrayList<String> strings = new ArrayList<>();
                            if (rs.next()){
                                strings.add(rs.getString(1));
                                strings.add(rs.getString(2));
                            }
                            rs.close();
                            preparedStatement.close();

                            gson.toJson(strings, ArrayList.class, writer);
                            writer.flush();
                        }
                    }catch (SQLException sqle){
                        System.out.println("SQLException: " + sqle.getMessage());
                        System.out.println("SQLState: " + sqle.getSQLState());
                        System.out.println("VendorError: " + sqle.getErrorCode());
                    }
                }finally {
                    socket.close();
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }finally {
            try{
                if (listener != null)
                    listener.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
                System.exit(-1);
            }

        }

    }
}
