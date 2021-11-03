package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.UUID;

public class Baltop implements CommandExecutor {

    private final SaulVaultEconomy economy;
    private final SaulConomy plugin;

    public Baltop(SaulVaultEconomy economy, SaulConomy plugin) {
        this.economy = economy;
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "----- Balance Top -----");

        int i = 0;

        for (Map.Entry<UUID, Double> entry : economy.balTop(plugin
                .getConfig()
                .getInt("maxBaltop"))
                .entrySet()) {
            i++;
            UUID uuid = entry.getKey();
            Double balance = entry.getValue();
            sender.sendMessage(ChatColor.WHITE + "" +  i + ". " + ChatColor.YELLOW + sender.getServer().getOfflinePlayer(uuid).getName() + " - $" + balance);
        }

        return true;
    }
}
