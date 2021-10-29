package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Balance implements CommandExecutor {

    private final SaulVaultEconomy economy;
    private final SaulConomy plugin;

    public Balance(SaulVaultEconomy economy, SaulConomy plugin) {
        this.economy = economy;
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED
                    + "This command can only be used by players");
            return true;
        }
        double bal = economy.getBalance((Player) sender);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("balMessage"))
                .replace("%name%", sender.getName())
                .replace("%amount%", String.valueOf(bal))));

        return true;
    }
}
