package me.mazenz.saulconomy;

import me.mazenz.saulconomy.commands.Balance;
import me.mazenz.saulconomy.commands.Pay;
import me.mazenz.saulconomy.commands.Saul;
import me.mazenz.saulconomy.commands.SetBalance;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class SaulConomy extends JavaPlugin {

    private Database db;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.db = new Database(this);
        this.db.load();

        // Vault integration
        SaulVaultEconomy economy = new SaulVaultEconomy(db);
        Bukkit.getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        // Commands
        getCommand("balance").setExecutor(new Balance(economy));
        getCommand("setbalance").setExecutor(new SetBalance(economy));
        getCommand("pay").setExecutor(new Pay(economy, this));
        getCommand("saul").setExecutor(new Saul());

        Logger logger = this.getLogger();

        new UpdateChecker(this, 82622).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("The Latest version of SaulConomy is running [0.1]");
            } else {
                logger.info("There is a new version of Extra Recipes. https://www.spigotmc.org/resources/extrarecipes.82622/");
            }
        });
    }

    @Override
    public void onDisable() {
        try {
            db.getConnection().close();
        } catch (SQLException exception) {
            db.report(exception);
        }
    }
}
