package me.mazenz.saulconomy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class sql {
    public static String host = SaulConomy.getInstance.getConfig().getString("host");
    public static int port = SaulConomy.getInstance.getConfig().getInt("port");
    public static String database = SaulConomy.getInstance.getConfig().getString("database");
    public static String username = SaulConomy.getInstance.getConfig().getString("username");
    public static String password = SaulConomy.getInstance.getConfig().getString("password");
    public static Connection con;

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void connect() throws SQLException {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                console.sendMessage("MYSQL CONNECTED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Economy (UUID VARCHAR(100), Balance DOUBLE(100), PRIMARY KEY (UUID))");
        ps.executeUpdate();
    }
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                console.sendMessage("MYSQL DISCONNECTED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isConnected() {
        return (con == null ? false : true);
    }
    public static Connection getConnection() {
        return con;
    }
}
