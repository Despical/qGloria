package me.despical.qgloria.menus.elements;

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

		var fireItemLore = config.getStringList("element-menu.fire-element.lore");
		var fireItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.fire-element.material")).orElse(XMaterial.FIRE_CHARGE))
			.name(Utils.center(config.getString("element-menu.fire-element.name"), fireItemLore))
			.lore(fireItemLore);

		var iceItemLore = config.getStringList("element-menu.ice-element.lore");
		var iceItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.ice-element.material")).orElse(XMaterial.HEART_OF_THE_SEA))
			.name(Utils.center(config.getString("element-menu.ice-element.name"), iceItemLore))
			.lore(iceItemLore);

		var earthItemLore = config.getStringList("element-menu.earth-element.lore");
		var earthItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.earth-element.material")).orElse(XMaterial.PITCHER_POD))
			.name(Utils.center(config.getString("element-menu.earth-element.name"), earthItemLore))
			.lore(earthItemLore);

		var airItemLore = config.getStringList("element-menu.air-element.lore");
		var airItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.air-element.material")).orElse(XMaterial.SNOWBALL))
			.name(Utils.center(config.getString("element-menu.air-element.name"), airItemLore))
			.lore(airItemLore);

		var user = menu.plugin.getUserManager().getUser(menu.player);
		var element = user.getElement();

		if (element != null && element.getId() != 0) {
			switch (element.getId()) {
				case 1 -> fireItem = fireItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
				case 2 -> iceItem = iceItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
				case 3 -> earthItem = earthItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
				case 4 -> airItem = airItem.enchantment(Enchantment.DURABILITY).flag(ItemFlag.HIDE_ENCHANTS);
			}
		}

		menu.pane.addItem(GuiItem.of(fireItem.build(), event(user, 1)), 1, 1);
		menu.pane.addItem(GuiItem.of(iceItem.build(), event(user, 2)), 3, 1);
		menu.pane.addItem(GuiItem.of(earthItem.build(), event(user, 3)), 5, 1);
		menu.pane.addItem(GuiItem.of(airItem.build(), event(user, 4)), 7, 1);
	}

	private Consumer<InventoryClickEvent> event(User user, int id) {
		return (event) -> {
			// TODO: 25.11.2023 - Enable selecting elements
//			if (user.getStat(StatsStorage.StatisticType.LEVEL) != 0) {
//				user.sendMessage("element-messages.cannot-change-element");
//				return;
//			}

			user.setElement(Utils.getElementFromId(user, id));
			user.closeInventory();
		};
	}
}