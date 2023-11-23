package me.despical.battleacademy.user.data;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.user.User;
import me.despical.commons.configuration.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public non-sealed class FileStatistics extends IUserDatabase {

	private final FileConfiguration config;

	public FileStatistics(Main plugin) {
		super(plugin);
		this.config = ConfigUtils.getConfig(plugin, "stats");
	}

	@Override
	public void saveStatistic(@NotNull User user, StatsStorage.StatisticType statisticType) {
		config.set(user.getUniqueId().toString() + "." + statisticType.getName(), user.getStat(statisticType));

		ConfigUtils.saveConfig(plugin, config, "stats");
	}

	@Override
	public void saveStatistics(@NotNull User user) {
		final String uuid = user.getUniqueId().toString();

		for (StatsStorage.StatisticType stat : StatsStorage.StatisticType.values()) {
			if (stat.isPersistent()) {
				config.set(uuid + "." + stat.getName(), user.getStat(stat));
			}
		}

		ConfigUtils.saveConfig(plugin, config, "stats");
	}

	@Override
	public void loadStatistics(@NotNull User user) {
		final String uuid = user.getUniqueId().toString();

		for (StatsStorage.StatisticType stat : StatsStorage.StatisticType.values()) {
			user.setStat(stat, config.getInt(uuid + "." + stat.getName()));
		}

		var levelManager = plugin.getLevelManager();

		levelManager.updatePlayerSpeed(user);
		levelManager.updatePlayerSpeed(user);
	}
}