package me.mazenz.saulconomy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class balance implements CommandExecutor {
    private SaulConomy plugin = SaulConomy.getInstance;
    private Economy e = plugin.economyImplementer;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    Player p = (Player) sender;
                    String uuid = p.getUniqueId().toString();
                    try {
                        PreparedStatement bal = sql.getConnection().prepareStatement("SELECT * FROM Economy WHERE UUID = '"+ uuid + "'");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String balance = ("SELECT * FROM Economy WHERE UUID = '"+ uuid + "'");
                    if (!plugin.playerBank.containsKey(p.getUniqueId())) {
                        plugin.playerBank.put(p.getUniqueId(), (double) 0);
                    }
                    double bal = e.getBalance(p);
                    p.sendMessage(ChatColor.RED + "Your current balance is: $" + bal);
                } else {
                    ((Player) sender).getPlayer().sendMessage(ChatColor.RED + "Incorrect Syntax. Please use /bal or /balance");
                }
            } else {
                System.out.println(ChatColor.RED + "This command can only be run in-game");
            }
        }
        return true;
    }
}
