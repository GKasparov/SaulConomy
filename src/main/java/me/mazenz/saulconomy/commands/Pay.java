package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Pay implements CommandExecutor {

    private final SaulVaultEconomy economy;
    private final SaulConomy plugin;

    public Pay(SaulVaultEconomy economy, SaulConomy plugin) {
        this.economy = economy;
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 2) {

            double minAmount = plugin.getConfig().getDouble("minimumPay");

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED
                        + "This command can only be run in-game");
                return true;
            }

            Player p = (Player) sender;

            if (!Helper.isDouble(args[1])) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin
                        .getConfig().
                        getString("notANumber")
                        .replace("%name%", p.getName())));
                return true;
            }

            double amount = Double.parseDouble(args[1]);

            if (!economy.has(p, amount)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin
                        .getConfig()
                        .getString("insufficientFundsMessage")
                        .replace("%player%", p.getName())));
                return true;
            }

            if (amount < minAmount) {
                p.sendMessage(ChatColor.RED
                        + "The amount you provided is invalid. The amount must be at least $"
                        + minAmount);
                return true;
            }

            if (economy.withdrawPlayer(p, amount).type != EconomyResponse.ResponseType.SUCCESS) {
                p.sendMessage(ChatColor.RED + "Unable to process transaction");
                return true;
            }

            economy.withdrawPlayer(p, amount);

            if (plugin.getConfig().getBoolean("offlinePayments")) {

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                economy.depositPlayer(target, amount);

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.
                        getConfig()
                        .getString("payMessage")
                        .replace("%targetPlayer", target.getName())
                        .replace("%name%", p.getName())
                        .replace("%amount%", String.valueOf(amount))));

                return true;
            }
            if (!(Bukkit.getPlayer(args[2]) instanceof Player)) {
                p.sendMessage(ChatColor.RED
                        + "That player that you specified does not exist or is not online");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);

            economy.depositPlayer(target, amount);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.
                    getConfig()
                    .getString("payMessage")
                    .replace("%targetPlayer%", target.getName())
                    .replace("%name%", p.getName())
                    .replace("%amount%", String.valueOf(amount))));

            return true;
        }

        return true;
    }
}
