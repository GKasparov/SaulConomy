package me.mazenz.saulconomy;

import org.bukkit.ChatColor;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class Database {

    private final SaulConomy plugin;
    private Connection connection;

    public Database(SaulConomy instance) {
        plugin = instance;
    }

    public Connection getConnection() {
        if (isOpen()) {
            return connection;
        }

        File database = new File(plugin.getDataFolder(), "economy.db");

        try {
            database.getParentFile().mkdirs();
            database.createNewFile();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "File write error: economy.db");
        }

        try {
            Class.forName("org.sqlite.JDBC");
            return connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        } catch (ClassNotFoundException | SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        }

        return null;
    }

    public void load() {
        connection = getConnection();

        try (Statement s = connection.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS economy (" +
                    "`uuid` varchar(32) NOT NULL, `balance` double(1000) NOT NULL, PRIMARY KEY (`uuid`));");
        } catch (SQLException exception) {
            report(exception);
        }
    }

    public boolean isOpen() {
        if (connection == null) {
            return false;
        }

        try {
            return !connection.isClosed();
        } catch (SQLException exception) {
            return false;
        }
    }

    public PreparedStatement statement(@Language("SQLite") String query, Initializer initializer) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(query);
        initializer.apply(statement);
        return statement;
    }

    public ResultSet result(@Language("SQLite") String query, Initializer initializer) throws SQLException {
        return statement(query, initializer).executeQuery();
    }

    public void report(SQLException exception) {
        plugin.getLogger().log(Level.SEVERE, ChatColor.RED + "Unhandled exception: " + exception.getMessage(), exception);
    }

    public interface Initializer {
        void apply(PreparedStatement s) throws SQLException;
    }
}
