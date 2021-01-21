package me.mazenz.saulconomy;

import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {
    SaulConomy plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "economy";
    public int tokens = 0;

    public Database(SaulConomy instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
    }

    // These are the methods you can use to get things out of your database. You of course can make new ones to return different things in the database.
    // This returns the number of people the player killed.
    public double getBalance(OfflinePlayer player) {
        Connection conn = getSQLConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE uuid = ?;")) {
            ps.setString(1, player.getUniqueId().toString());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement", ex);
        }

        return 0.0;
    }

    public void setBalance(OfflinePlayer player, double balance) {
        // TODO: make this work
    }
}
