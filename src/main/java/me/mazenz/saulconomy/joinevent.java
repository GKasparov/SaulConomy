package me.mazenz.saulconomy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;

public class joinevent implements Listener {
    @EventHandler

    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        String uuid = p.getUniqueId().toString();
        try {
            PreparedStatement query = sql.getConnection().prepareStatement("SELECT * FROM Economy WHERE UUID = '" + uuid + "'");
            ResultSet results = query.executeQuery();
            if (results.next()) {
                System.out.println("Player with UUID " + uuid + " Found");
            } else {
                PreparedStatement addPlayer = sql.getConnection().prepareStatement("INSERT INTO Economy (UUID, Balance) VALUES ('" + uuid + "', 0)");
                System.out.println("Added player with uuid " + uuid + " Into the DB");
                addPlayer.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
