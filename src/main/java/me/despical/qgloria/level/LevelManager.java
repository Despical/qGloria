package me.despical.qgloria.level;

import me.despical.qgloria.Main;
import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.user.User;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.miscellaneous.AttributeUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class LevelManager {

	private final Main plugin;
	private final Set<Level> levels;

	public LevelManager(Main plugin) {
		this.plugin = plugin;
		this.levels = new HashSet<>();

		this.registerLevels(plugin);
	}

	public void addXP(User user, int xp) {
		final var currentLevel = getLevel(user);

		if (currentLevel.getLevel() < 3) {
			xp += ThreadLocalRandom.current().nextInt(5, 30);
		}

		final var currentLevelXP = currentLevel.getXp();
		final var playerXP = user.getStat(StatsStorage.StatisticType.XP);

		user.addStat(StatsStorage.StatisticType.XP, xp);

		if (currentLevel.getLevel() == getLastLevel()) return;

		if (playerXP + xp >= currentLevelXP) {
			user.addStat(StatsStorage.StatisticType.LEVEL, 1);

			updatePlayerSpeed(user);

			user.sendMessage("level-messages.new-level");
			user.sendTitle("level-messages.level-up-title","level-messages.level-up-subtitle");
		}
	}

	public Level getLevel(int level) {
		return levels.stream().filter(l -> l.getLevel() == level).findFirst().get();
	}

	public int getMax() {
		return levels.stream().map(Level::getLevel).max(Integer::compareTo).get();
	}

	public Level getLevel(User user) {
		final int level = Math.max(1, user.getStat(StatsStorage.StatisticType.LEVEL));

		return levels.stream().filter(l -> l.getLevel() == level).findFirst().orElse(null);
	}

	public int getLastLevel() {
		return levels.stream().map(Level::getLevel).max(Comparator.naturalOrder()).orElse(-1);
	}

	private void registerLevels(Main plugin) {
		final var config = ConfigUtils.getConfig(plugin, "levels");
		final var section = config.getConfigurationSection("levels");

		if (section == null) {
			plugin.getLogger().warning("There is no 'levels' section found in the file!");
			return;
		}

		for (final var levelKey : section.getKeys(false)) {
			final var level = Integer.parseInt(levelKey);
			final var xp = config.getInt("levels.%s.xp".formatted(levelKey));
			final var tier = config.getInt("levels.%s.tier".formatted(levelKey));
			final var tierMessage = config.getString("tiers.%d".formatted(tier));
			final var special = config.getBoolean("levels.%s.special".formatted(levelKey));

			this.levels.add(new Level(level, xp, special, tierMessage));
		}
	}

	public void updatePlayerSpeed(User user) {
		var currentLevel = getLevel(user).getLevel();

		var player = user.getPlayer();

		player.setWalkSpeed(.2F + (float) getModifier("speed", currentLevel));
	}

	public double getModifier(String modifierName, int currentLevel) {
		double modifier = 0;

		final var config = ConfigUtils.getConfig(plugin, "levels");
		final var section = config.getConfigurationSection("levels");

		for (final var levelKey : section.getKeys(false)) {
			var level = Integer.parseInt(levelKey);

			if (level > currentLevel) break;

			modifier += config.getDouble("levels.%s.%s".formatted(levelKey, modifierName), 0);
		}

		return modifier;
	}
}