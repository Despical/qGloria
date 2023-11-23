package me.despical.battleacademy.user;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.user.data.FileStatistics;
import me.despical.battleacademy.user.data.IUserDatabase;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserManager {

	@NotNull
	private final Set<User> users;

	@NotNull
	private final IUserDatabase userDatabase;

	public UserManager(Main plugin) {
		this.users = new HashSet<>();
		this.userDatabase = new FileStatistics(plugin);

		plugin.getServer().getOnlinePlayers().stream().map(this::getUser).forEach(this::loadStatistics);
	}

	@NotNull
	public User addUser(final Player player) {
		final var user = new User(player.getUniqueId());

		this.users.add(user);
		return user;
	}

	public void removeUser(final User user) {
		this.users.remove(user);
	}

	@NotNull
	public User getUser(final Player player) {
		final var uuid = player.getUniqueId();

		for (final var user : this.users) {
			if (uuid.equals(user.getUniqueId())) {
				return user;
			}
		}

        return this.addUser(player);
	}

	@NotNull
	public Set<User> getUsers() {
		return Set.copyOf(users);
	}

	@NotNull
	public IUserDatabase getUserDatabase() {
		return this.userDatabase;
	}

	public void saveStatistic(final User user, final StatsStorage.StatisticType statisticType) {
		if (!statisticType.isPersistent()) return;

		this.userDatabase.saveStatistics(user);
	}

	public void saveStatistics(final User user) {
		this.userDatabase.saveStatistics(user);
	}

	public void loadStatistics(final User user) {
		this.userDatabase.loadStatistics(user);
	}
}