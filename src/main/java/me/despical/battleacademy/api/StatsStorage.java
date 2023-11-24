package me.despical.battleacademy.api;

import me.despical.battleacademy.Main;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.sorter.SortUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class StatsStorage {

	private static final Main plugin = JavaPlugin.getPlugin(Main.class);

	@NotNull
	@Contract("null -> fail")
	public static Map<UUID, Integer> getStats(StatisticType stat) {
		final var config = ConfigUtils.getConfig(plugin, "stats");
		final var stats = new LinkedHashMap<UUID, Integer>();

		for (var string : config.getKeys(false)) {
			stats.put(UUID.fromString(string), config.getInt(string + "." + stat.getName()));
		}

		return SortUtils.sortByValue(stats);
	}

	public static double getUserStats(final Player player, final StatisticType statisticType) {
		return plugin.getUserManager().getUser(player).getStat(statisticType);
	}

	public enum StatisticType {

		KILLS("kills", true), DEATHS("deaths", true), LEVEL("level", true),
		XP("xp", true), DAMAGE_TAKEN("taken_damage", true);

		final String statisticName;
		final boolean persistent;

		StatisticType(String statisticName, boolean persistent) {
			this.statisticName = statisticName;
			this.persistent = persistent;
		}

		@NotNull
		public String getName() {
			return statisticName;
		}

		public boolean isPersistent() {
			return persistent;
		}
	}
}