package me.despical.battleacademy.user;

import lombok.Getter;
import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.elements.base.Element;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

	private static final Main plugin = JavaPlugin.getPlugin(Main.class);
	private static long cooldownCounter = 0;

	private @Getter Element element;

	private final @Getter UUID uniqueId;
	private final Map<String, Double> cooldowns;
	private final Map<StatsStorage.StatisticType, Integer> statistics;

	public User(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.statistics = new HashMap<>();
		this.cooldowns = new HashMap<>();
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

	public void closeInventory() {
		getPlayer().closeInventory();
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

	public int getLevel() {
		return plugin.getLevelManager().getLevel(this).getLevel();
	}

	public void addStat(StatsStorage.StatisticType stat, int value) {
		setStat(stat, getStat(stat) + value);
	}

	public void setElement(Element element) {
		this.setStat(StatsStorage.StatisticType.ELEMENT, element.getId());
		this.element = element;

		var userManager = plugin.getUserManager();

		if (userManager != null)
			userManager.saveStatistic(this, StatsStorage.StatisticType.ELEMENT);
	}

	public void setCooldown(String s, double seconds) {
		cooldowns.put(s, seconds + cooldownCounter);
	}

	public double getCooldown(String s) {
		final Double cooldown = cooldowns.get(s);

		return (cooldown == null || cooldown <= cooldownCounter) ? 0 : cooldown - cooldownCounter;
	}

	public static void cooldownHandlerTask() {
		plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> cooldownCounter++, 20, 20);
	}
}