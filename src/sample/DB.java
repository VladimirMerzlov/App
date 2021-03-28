package sample;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "8889";
    private final String DB_NAME = "app_db";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;

    }

    public void isConnected () throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));

    }

    public boolean regUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES (?, ?, ?) " ;

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login +  "' LIMIT 1");
        if(res.next())
            return  false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
         prSt.setString(1, login);
         prSt.setString(2, email);
         prSt.setString(3, password);
         prSt.executeUpdate();

         return true;


    }

    public boolean authUser(String login, String pass) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login +  "' AND `password` = '" + pass + "' LIMIT 1" ;
        ResultSet res = statement.executeQuery(sql);

        return res.next();

    }

}
