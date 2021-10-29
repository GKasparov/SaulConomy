package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetBalance implements CommandExecutor {

    private final SaulVaultEconomy economy;
    private final SaulConomy plugin;

    public SetBalance(SaulVaultEconomy economy, SaulConomy plugin) {
        this.economy = economy;
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(args.length == 2)) {
            sender.sendMessage(ChatColor.RED
                    + "Incorrect Syntax: /setbal <player> <amount>");
            return true;
        }

        if (!Helper.isDouble(args[1])) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin
                    .getConfig()
                    .getString("notANumber"))));
            return true;
        }

        if (sender instanceof Player && !sender.hasPermission("saul.setbalance") && !sender.hasPermission("saul.*")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin
                            .getConfig()
                            .getString("noPermission"))
                    .replace("%name%", sender.getName())));
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

