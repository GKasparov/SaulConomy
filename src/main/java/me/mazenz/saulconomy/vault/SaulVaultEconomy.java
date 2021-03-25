package me.mazenz.saulconomy.vault;

import me.mazenz.saulconomy.Database;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SaulVaultEconomy implements BaseVaultImplementation {

    private static final EconomyResponse NOT_IMPLEMENTED = new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");

    private final Database db;

    public SaulVaultEconomy(Database db) {
        this.db = db;
    }

    @Override
    public boolean isEnabled() {
        return db.isOpen();
    }

    @Override
    public String getName() {
        return "SaulConomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double amount) {
        return Double.toString(amount);
    }

    @Override
    public String currencyNamePlural() {
        return "Dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "Dollar";
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        try (ResultSet resultSet = db.result("SELECT * FROM economy WHERE uuid = ?", s -> {
            s.setString(1, offlinePlayer.getUniqueId().toString());
        })) {
            return resultSet.next();
        } catch (SQLException exception) {
            db.report(exception);
        }

        return false;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        try (ResultSet resultSet = db.result("SELECT balance FROM economy WHERE uuid = ?", s -> {
            s.setString(1, player.getUniqueId().toString());
        })) {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            db.report(exception);
        }

        return 0;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        return setBalance(player, getBalance(player) - amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        return setBalance(player, getBalance(player) + amount);
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return NOT_IMPLEMENTED;
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        try (PreparedStatement statement = db.statement("INSERT OR IGNORE INTO economy VALUES (?, ?)", s -> {
            s.setString(1, player.getUniqueId().toString());
            s.setDouble(2, 0);
        })) {
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            db.report(exception);
        }

        return false;
    }

    public EconomyResponse setBalance(OfflinePlayer player, double balance) {
        if (balance < 0) {
            return new EconomyResponse(balance, balance, EconomyResponse.ResponseType.FAILURE, "Negative balance");
        }

        createPlayerAccount(player);

        try (PreparedStatement statement = db.statement("UPDATE economy SET balance = ? WHERE uuid = ?", s -> {
            s.setDouble(1, balance);
            s.setString(2, player.getUniqueId().toString());
        })) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            db.report(exception);
        }

        return new EconomyResponse(balance, balance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    public Map<UUID, Double> balTop(int maxResults) {
        Map<UUID, Double> top = new LinkedHashMap<>();

        try (ResultSet resultSet = db.result("SELECT * FROM economy ORDER BY balance DESC LIMIT ?", s -> {
            s.setInt(1, maxResults);
        })) {
            while (resultSet.next()) {
                top.put(UUID.fromString(resultSet.getString(1)), resultSet.getDouble(2));
            }
        } catch (SQLException exception) {
            db.report(exception);
        }

        return top;
    }
}
