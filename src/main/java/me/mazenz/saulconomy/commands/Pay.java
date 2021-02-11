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

public class Pay implements CommandExecutor {

    private final SaulVaultEconomy economy;
    private final SaulConomy plugin;

    public Pay(SaulVaultEconomy economy, SaulConomy plugin) {
        this.economy = economy;
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (sender instanceof Player) {
                if (plugin.getConfig().getBoolean("offlinePayments")) {
                    Player p = (Player) sender;
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    double minAmount = plugin.getConfig().getDouble("minimumPay");

                    if (Helper.isDouble(args[1])) {
                        double amount = Double.parseDouble(args[1]);
                        if (economy.has(p, amount)) {
                            if (amount >= minAmount) {
                                if (economy.withdrawPlayer(p, amount).type == EconomyResponse.ResponseType.SUCCESS) {
                                    economy.depositPlayer(target, amount);
                                    p.sendMessage("Transaction successful");
                                } else {
                                    p.sendMessage(ChatColor.RED + "Unable to process transaction");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "The amount you provided is invalid. The amount must be at least $" + minAmount);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Insufficient funds for this transaction");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "The number you provided is invalid");
                    }
                } else {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player p = (Player) sender;
                        Player target = Bukkit.getPlayer(args[0]);
                        double minAmount = plugin.getConfig().getDouble("minimumPay");

                        if (Helper.isDouble(args[1])) {
                            double amount = Double.parseDouble(args[1]);
                            if (economy.has(p, amount)) {
                                if (amount >= minAmount) {
                                    if (economy.withdrawPlayer(p, amount).type == EconomyResponse.ResponseType.SUCCESS) {
                                        economy.depositPlayer(target, amount);
                                        p.sendMessage(ChatColor.YELLOW + "Transaction successful");
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Unable to process transaction");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "The amount you provided is invalid. The amount must be at least $" + minAmount);
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Insufficient funds for this transaction");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "The number you provided is invalid");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "The player you provided does not exist or is not currently online");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be run in-game");
            }
        }

        return true;
    }
}
