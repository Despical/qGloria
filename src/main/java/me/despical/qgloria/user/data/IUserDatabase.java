package me.despical.qgloria.user.data;

import me.despical.qgloria.Main;
import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.user.User;
import org.jetbrains.annotations.NotNull;

public abstract sealed class IUserDatabase permits FileStatistics {

	@NotNull
	protected final Main plugin;

	public IUserDatabase(final @NotNull Main plugin) {
		this.plugin = plugin;
	}

	public abstract void saveStatistic(final @NotNull User user, final StatsStorage.StatisticType statisticType);

	public abstract void saveStatistics(final @NotNull User user);

	public abstract void loadStatistics(final @NotNull User user);
}