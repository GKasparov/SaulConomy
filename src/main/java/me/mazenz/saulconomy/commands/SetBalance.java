package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBalance implements CommandExecutor {

    private final SaulVaultEconomy economy;

    public SetBalance(SaulVaultEconomy economy) {
        this.economy = economy;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("saul.setbalance") || p.hasPermission("saul.*")) {
                    if (Helper.isDouble(args[1])) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        double bal = Double.parseDouble(args[1]);
                        economy.setBalance(target, bal);
                        String name = target.getName();
                        p.sendMessage(ChatColor.YELLOW + "You have set " + name + "'s balance to $" + bal);
                    } else {
                        p.sendMessage(ChatColor.RED + "The number you gave is not valid");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Insufficient Permissions");

                }
                return true;
            }

            if (Helper.isDouble(args[1])) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                String name = target.getName();
                double bal = Double.parseDouble(args[1]);
                economy.setBalance(target, bal);
                sender.sendMessage(ChatColor.YELLOW + "You have set " + name + "'s balance to $" + bal);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Incorrect Syntax: /setbal <player> <amount>");

        }
        return true;
    }
}
