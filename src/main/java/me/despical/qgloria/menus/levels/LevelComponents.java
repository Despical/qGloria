package me.despical.qgloria.menus.levels;

import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.commons.util.function.DoubleSupplier;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.GuiItem;
import me.despical.inventoryframework.pane.PaginatedPane;
import me.despical.inventoryframework.pane.StaticPane;
import me.despical.qgloria.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Despical
 * <p>
 * Created at 16.12.2023
 */
public class LevelComponents {

	private static final List<XY> FIRST_PAGE = List.of(xy(0, 0), xy(0, 1), xy(0, 2), xy(0, 3), xy(1, 3), xy(2, 3),
		xy(2, 2), xy(2, 1), xy(2, 0), xy(3, 0), xy(4, 0), xy(4, 1), xy(4, 2), xy(4, 3), xy(5, 3),
		xy(6, 3), xy(6, 2), xy(6, 1), xy(6, 0), xy(7, 0), xy(8, 0), xy(8, 1), xy(8, 2), xy(8, 3));

	private static final List<XY> SECOND_PAGE = List.of(xy(0, 3), xy(1, 3), xy(1, 2), xy(1, 1), xy(1, 1), xy(1, 0), xy(2, 0),
		xy(3, 0), xy(3, 1), xy(3, 2), xy(3, 3), xy(4, 3), xy(5, 3), xy(5, 2), xy(5, 1), xy(5, 0), xy(6, 0),
		xy(7, 0), xy(7, 1), xy(7, 2), xy(7, 3), xy(8, 3));

	private final LevelsMenu menu;

	public LevelComponents(LevelsMenu menu) {
		this.menu = menu;
	}

	public void injectComponents(LevelsMenu menu) {
		var plugin = menu.plugin;
		var user = plugin.getUserManager().getUser(menu.player);
		var level = user.getLevel();
		var paginatedPane = menu.paginatedPane;
		var remainingLevels = plugin.getLevelManager().getMax();
		var maxConstant = plugin.getLevelManager().getMax();
		var page = 0;
		var lastLevel = 0;

		boolean decreaseFirst = true;

		while (remainingLevels > 0) {
			remainingLevels -= decreaseFirst ? 24 : 22;

			decreaseFirst = !decreaseFirst;

			StaticPane staticPane = new StaticPane(9, 6);
			addMoveItems(staticPane, paginatedPane, menu.gui);

			if (remainingLevels >= 0) {
				addLevelItems(lastLevel, maxConstant, level, staticPane, FIRST_PAGE);

				lastLevel += 24;
			} else {
				addLevelItems(lastLevel, maxConstant, level, staticPane, SECOND_PAGE);

				lastLevel += 19;
			}

			paginatedPane.addPane(page++, staticPane);
		}
	}

	private void addMoveItems(StaticPane pane, PaginatedPane paginatedPane, Gui gui) {
		pane.addItem(GuiItem.of(XMaterial.ARROW.parseItem(), event -> {
				paginatedPane.setPage(Math.max(0, paginatedPane.getPage() - 1));
			gui.update();
		}), 0, 5);

		pane.addItem(GuiItem.of(XMaterial.ARROW.parseItem(), event -> {
			paginatedPane.setPage(Math.min(paginatedPane.getPages() - 1, paginatedPane.getPage() + 1));
			gui.update();
		}), 8, 5);
	}

	private XMaterial getSpecial(int level, int requiredLevel) {
		return level >= requiredLevel ? XMaterial.COOKIE : XMaterial.COAL_BLOCK;
	}

	private List<String> getRewards(Main plugin, int level) {
		var config = ConfigUtils.getConfig(plugin, "levels");
		List<String> lore = new ArrayList<>();

		DoubleSupplier<String, Boolean> test = (path) -> config.isSet("levels.%d.%s".formatted(level, path));
		DoubleSupplier<String, Double> val = (path) -> config.getDouble("levels.%d.%s".formatted(level, path));

		if (test.accept("attack")) {
			lore.add("&8+&7%.3f &7Saldırı".formatted(val.accept("attack")));
		}

		if (test.accept("attack-speed")) {
			lore.add("&8+&7%.3f &7Atak Hızı".formatted(val.accept("attack-speed")));
		}

		if (test.accept("critical-hit")) {
			lore.add("&8+&7%.3f &7Kritik Vuruş Hasarı".formatted(val.accept("critical-hit")));
		}

		if (test.accept("defense")) {
			lore.add("&8+&7%.3f &7Defans".formatted(val.accept("defense")));
		}

		if (test.accept("fall-damage-defense")) {
			lore.add("&8-&7%.3f &7Düşme Hasarı".formatted(val.accept("fall-damage-defense")));
		}

		if (test.accept("fire-defense")) {
			lore.add("&8+&7%.3f &7Ateş Koruması".formatted(val.accept("fire-defense")));
		}

		if (test.accept("lava-defense")) {
			lore.add("&8+&7%.3f &7Lav Koruması".formatted(val.accept("lava-defense")));
		}

		if (test.accept("speed")) {
			lore.add("&8+&7%.3f &7Hız".formatted(val.accept("speed")));
		}

		if (test.accept("saturation")) {
			lore.add("&8+&7%.3f &7Açlık Doldurma".formatted(val.accept("saturation")));
		}

		List<String> newLore = new ArrayList<>();

		if (lore.size() == 1) {
			newLore.add("&7Ödül:");
			newLore.addAll(lore);
			return newLore;
		} else if (lore.size() > 1) {
			newLore.add("&7Ödüller:");
			newLore.addAll(lore);
			return newLore;
		}

		return lore;
	}

	private void addLevelItems(int lastLevel, int maxConstant, int level, StaticPane staticPane, List<XY> list) {

		var plugin = menu.plugin;
		var config = ConfigUtils.getConfig(plugin, "menu");
		Function<String, String> getStr = (path) -> config.getString("level-menu." + path);

		for (int i = 0; i < list.size(); i++) {
			ItemBuilder builder;
			int currentLevel = i + 1 + lastLevel;

			if (currentLevel > maxConstant) break;

			if (level > currentLevel) {
				builder = new ItemBuilder(XMaterial.matchXMaterial(getStr.apply("unlocked-level.item")).get()).name(getStr.apply("unlocked-level.name"));
			} else {
				builder = new ItemBuilder(XMaterial.matchXMaterial(getStr.apply("locked-level.item")).get()).name(getStr.apply("locked-level.name"));
			}

			var realLevel = plugin.getLevelManager().getLevel(currentLevel);

			if (realLevel.isSpecial()) {
				builder = new ItemBuilder(getSpecial(level, currentLevel)).name(level > i + 1 ? getStr.apply("unlocked-special-level") : getStr.apply("locked-special-level"));
			}

			var lore = config.getStringList("level-menu.unlocked-level.lore").stream()
				.map(msg -> {
					msg = msg.replace("%tier_message%", realLevel.getTierMessage());
					msg = msg.replace("%needed_exp%", Integer.toString(realLevel.getXp()));
					msg = msg.replace("%locked_message%", level > currentLevel ? getStr.apply("unlocked-message") : getStr.apply("locked-message"));
					return msg;
				}).collect(Collectors.toList());

			for (int j = 0; j < lore.size(); j++) {
				if (lore.get(j).equals("%rewards%")) {
					var rewardsLore = getRewards(plugin, currentLevel);

					if (rewardsLore.isEmpty()) {
						lore.set(j, "");
						break;
					}

					var firstPart = new ArrayList<>(lore.subList(0, j));
					var secondPart = new ArrayList<>(lore.subList(j + 1, lore.size()));

					firstPart.add("");
					firstPart.addAll(rewardsLore);
					firstPart.add("");
					firstPart.addAll(secondPart);
					lore = firstPart;
					break;
				}
			}

			builder.lore(lore);

			var xy = list.get(i);

			staticPane.addItem(GuiItem.of(builder.build(), event -> event.setCancelled(true)), xy.x, xy.y);
		}
	}

	private static XY xy(int x, int y) {
		return new XY(x, y);
	}

	private record XY(int x, int y) {
	}
}