package me.mazenz.saulconomy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class onjoin implements Listener {
    private final Database db;

    public onjoin(Database db) {
        this.db = db;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String uuid = p.getUniqueId().toString();

        try (PreparedStatement findUser = db.connection.prepareStatement("SELECT * FROM economy WHERE uuid = ?")) {
            findUser.setString(1, uuid);

            try (ResultSet results = findUser.executeQuery()) {
                if (results.next()) {
                    System.out.println("Player with UUID " + uuid + " exists in the DataBase");
                } else {
                    try (PreparedStatement setUser = db.connection.prepareStatement("INSERT INTO economy (uuid, balance) VALUES (?, ?)")) {
                        setUser.setString(1, uuid);
                        double bal = 0.0;
                        setUser.setDouble(2, bal);
                        setUser.executeUpdate();
                    }
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
