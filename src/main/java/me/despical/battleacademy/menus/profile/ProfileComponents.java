package me.despical.battleacademy.menus.profile;

import me.despical.battleacademy.api.StatsStorage;
import me.despical.commons.item.ItemBuilder;
import me.despical.commons.item.ItemUtils;
import me.despical.inventoryframework.GuiItem;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileComponents {

	public void injectComponents(ProfileMenu menu) {
		var skull = ItemUtils.PLAYER_HEAD_ITEM.clone();
		var skullMeta = (SkullMeta) skull.getItemMeta();

		ItemUtils.setPlayerHead(menu.player, skullMeta);

		skull.setItemMeta(skullMeta);

		var user = menu.plugin.getUserManager().getUser(menu.player);
		var skullItem = new ItemBuilder(skull)
			.name("&a" + menu.player.getName())
			.lore("&7Level: &6" + user.getStat(StatsStorage.StatisticType.LEVEL))
			.lore("&7Experience Points: &e" + user.getStat(StatsStorage.StatisticType.XP))
			.build();

		menu.pane.addItem(GuiItem.of(skullItem), 4, 0);

	}
}