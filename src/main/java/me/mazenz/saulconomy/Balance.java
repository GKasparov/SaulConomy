package me.mazenz.saulconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Balance implements CommandExecutor {
    private final Database db;

    public Balance(Database db) {
        this.db = db;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String uuid = p.getUniqueId().toString();
            p.sendMessage("Your old balance is " + db.getBalance(p));

            try (PreparedStatement addMonies = db.connection.prepareStatement("UPDATE economy SET balance = ? WHERE uuid = ?")) {
                addMonies.setDouble(1, 111.1);
                addMonies.setString(2, uuid);
                addMonies.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return true;
    }
}
