package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        int pageToShow;
        int pageAmount = plugin.getConfig().getInt("pageAmount");
        if (args.length == 0) {
            pageToShow = 1;
        } else {
            try {

                pageToShow = Integer.parseInt(args[0]);

            } catch (NumberFormatException e) {

                String preOutput = plugin.getConfig().getString("notANumber");

                if (!(sender instanceof Player)) {

                    preOutput = preOutput.replace("%name%", "console");
                } else {

                    preOutput = preOutput.replace("%name%", sender.getName());
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', preOutput));

                return true;
            }
        }

        int skip = pageAmount * (pageToShow - 1);

        for (Map.Entry<UUID, Double> entry : economy.balTop().entrySet()) {
            if (--skip > 0) {
                continue;
            }

            if (i >= pageAmount) {
                break;
            }
            i++;
            UUID uuid = entry.getKey();
            Double balance = entry.getValue();

            sender.sendMessage(ChatColor.WHITE + "" +
                    (i + (pageAmount * (pageToShow - 1))) + ". " +
                    ChatColor.YELLOW +
                    sender.getServer().getOfflinePlayer(uuid).getName() + " - $" + balance);
        }

        return true;
    }
}
