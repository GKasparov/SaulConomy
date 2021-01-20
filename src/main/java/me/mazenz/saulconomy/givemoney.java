package me.mazenz.saulconomy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class givemoney implements CommandExecutor {
    private SaulConomy plugin = SaulConomy.getInstance;
    private Economy e = plugin.economyImplementer;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("givemoney")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("saulconomy.givemoney") || p.hasPermission("saulconomy.*")) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Double amount = Double.valueOf(args[1]);

                        if (target != null) {
                            if (!e.hasAccount(target)) {
                                plugin.playerBank.put(target.getUniqueId(), (double) 0);
                            }
                            e.depositPlayer(target, amount);
                            p.sendMessage(ChatColor.RED + "$" + amount + " has been put into " + target + "'s account");
                        } else {
                            OfflinePlayer offlinetarget = Bukkit.getOfflinePlayer(args[0]);
                            if (!e.hasAccount(offlinetarget)) {
                                e.createPlayerAccount(offlinetarget);
                            }
                            e.depositPlayer(offlinetarget, amount);
                            p.sendMessage(ChatColor.RED + "$" + amount + " has been put into " + offlinetarget + "'s account");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Insufficient Permissions");
                }
            }
        }
        return true;
    }
}