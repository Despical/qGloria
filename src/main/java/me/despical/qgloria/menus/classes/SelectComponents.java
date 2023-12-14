package me.despical.qgloria.menus.classes;

import me.despical.qgloria.user.User;
import me.despical.qgloria.util.Utils;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.inventoryframework.GuiItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.function.Consumer;

public class SelectComponents {

	public void injectComponents(SelectMenu menu) {
		final var config = ConfigUtils.getConfig(menu.plugin, "menu");

		var barbarianItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("classes.barbarian.material")).orElse(XMaterial.FIRE_CHARGE))
			.name(config.getString("classes.barbarian.name"))
			.lore(config.getStringList("classes.barbarian.lore"));

		var kurianItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("classes.kurian.material")).orElse(XMaterial.STONE))
			.name(config.getString("classes.kurian.name"))
			.lore(config.getStringList("classes.kurian.lore"));

		var karusItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("classes.karus.material")).orElse(XMaterial.SNOWBALL))
			.name(config.getString("classes.karus.name"))
			.lore(config.getStringList("classes.karus.lore"));

		var user = menu.plugin.getUserManager().getUser(menu.player);
		var element = user.getElement();

		if (element != null && element.getId() != 0) {
			switch (element.getId()) {
				case 1 -> barbarianItem = barbarianItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
				case 2 -> kurianItem = kurianItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
				case 3 -> karusItem = karusItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
			}
		}

		menu.pane.addItem(GuiItem.of(barbarianItem.build(), event(user, 1)), 2, 1);
		menu.pane.addItem(GuiItem.of(kurianItem.build(), event(user, 3)), 4, 1);
		menu.pane.addItem(GuiItem.of(karusItem.build(), event(user, 4)), 6, 1);
	}

	private Consumer<InventoryClickEvent> event(User user, int id) {
		return (event) -> {
			// TODO: 25.11.2023 - Enable selecting elements
//			if (user.getStat(StatsStorage.StatisticType.LEVEL) != 0) {
//				user.sendMessage("messages.cannot-change-class");
//				return;
//			}

			user.setElement(Utils.getElementFromId(user, id));
			user.closeInventory();
		};
	}
}