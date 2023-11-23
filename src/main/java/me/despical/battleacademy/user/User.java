package me.despical.battleacademy.user;

import lombok.Getter;
import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

	private static final Main plugin = JavaPlugin.getPlugin(Main.class);

	private final @Getter UUID uniqueId;
	private final Map<StatsStorage.StatisticType, Integer> statistics;

	public User(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.statistics = new HashMap<>();
	}

	public void sendMessage(final String path) {
		this.sendRawMessage(plugin.getChatManager().message(path));
	}

	public void sendMessage(final String path, final Object... args) {
		this.sendRawMessage(plugin.getChatManager().message(path), args);
	}

	public void sendRawMessage(final String message) {
		this.getPlayer().sendMessage(plugin.getChatManager().rawMessage(message));
	}

	public void sendRawMessage(final String message, final Object... args) {
		this.getPlayer().sendMessage(plugin.getChatManager().rawMessage(String.format(message, args)));
	}

	public String getName() {
		return getPlayer().getName();
	}

	public Player getPlayer() {
		return plugin.getServer().getPlayer(uniqueId);
	}

	public int getStat(StatsStorage.StatisticType statisticType) {
		return statistics.computeIfAbsent(statisticType, stat -> 0);
	}

	public void setStat(StatsStorage.StatisticType stat, int value) {
		if (stat == StatsStorage.StatisticType.LEVEL && value == 0) {
			statistics.put(StatsStorage.StatisticType.LEVEL, 1);
			return;
		}

		statistics.put(stat, value);
	}

	public void addStat(StatsStorage.StatisticType stat, int value) {
		setStat(stat, getStat(stat) + value);
	}
}