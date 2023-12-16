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
				addLevelItems(plugin, lastLevel, maxConstant, level, staticPane, FIRST_PAGE);

				lastLevel += 24;
			} else {
				addLevelItems(plugin, lastLevel, maxConstant, level, staticPane, SECOND_PAGE);

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

		if (lore.size() == 1) {
			List<String> newLore = new ArrayList<>();
			newLore.add("&7Ödül:");
			newLore.addAll(lore);
			newLore.add("");
			return newLore;
		} else if (lore.size() > 1) {
			List<String> newLore = new ArrayList<>();
			newLore.add("&7Ödüller:");
			newLore.addAll(lore);
			newLore.add("");
			return newLore;
		}

		return lore;
	}

	private void addLevelItems(Main plugin, int lastLevel, int maxConstant, int level, StaticPane staticPane, List<XY> list) {
		for (int i = 0; i < list.size(); i++) {
			ItemBuilder builder;
			int currentLevel = i + 1 + lastLevel;

			if (currentLevel > maxConstant) break;

			if (level > currentLevel) {
				builder = new ItemBuilder(XMaterial.GOLD_INGOT).name("&a&lNormal &aSeviye Ödülü");
			} else {
				builder = new ItemBuilder(XMaterial.COAL).name("&a&lNormal &cSeviye Ödülü");
			}

			var realLevel = plugin.getLevelManager().getLevel(currentLevel);

			if (realLevel.isSpecial()) {
				builder = new ItemBuilder(getSpecial(level, currentLevel)).
					name(level > i + 1 ? "&6&lÖzel &aSeviye Ödülü" : "&6&lÖzel &cSeviye Ödülü");
			}

			builder.lore(realLevel.getTierMessage(), "").
				lore("&7Gerekli Puan: &e" + realLevel.getXp(), "").
				lore(getRewards(plugin, currentLevel)).
				lore(level > currentLevel ? "&aZaten bu ödülü aldın!" : "&cBu ödülü şu an alamazsın!");

			var xy = list.get(i);

			staticPane.addItem(GuiItem.of(builder.build(), event -> event.setCancelled(true)), xy.x, xy.y);
		}
	}

	private static XY xy(int x, int y) {
		return new XY(x, y);
	}

	private static class XY {

		final int x, y;

		public XY(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}