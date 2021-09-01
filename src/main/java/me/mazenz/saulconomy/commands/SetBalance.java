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
        if (!(args.length == 2)) {
            sender.sendMessage(ChatColor.RED
                    + "Incorrect Syntax: /setbal <player> <amount>");
        }

        if (!Helper.isDouble(args[1])) {
            sender.sendMessage(ChatColor.RED
                    + "The number you gave is not valid");
            return true;
        }

        if (sender instanceof Player && !sender.hasPermission("saul.setbalance") && !sender.hasPermission("saul.*")) {
            sender.sendMessage(ChatColor.RED +
                    "Insufficient Permissions");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double bal = Double.parseDouble(args[1]);
        economy.setBalance(target, bal);
        String name = target.getName();
        sender.sendMessage(ChatColor.YELLOW
                + "You have set "
                + name
                + "'s balance to $"
                + bal);

        return true;
    }
}

