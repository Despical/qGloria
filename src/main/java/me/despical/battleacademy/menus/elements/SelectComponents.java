package me.despical.battleacademy.menus.elements;

import me.despical.battleacademy.util.Utils;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.inventoryframework.GuiItem;

public class SelectComponents {

	public void injectComponents(SelectMenu menu) {
		final var config = ConfigUtils.getConfig(menu.plugin, "menu");

		var fireItemLore = config.getStringList("element-menu.fire-element.lore");
		var fireItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.fire-element.material")).orElse(XMaterial.FIRE_CHARGE))
			.name(Utils.center(config.getString("element-menu.fire-element.name"), fireItemLore))
			.lore(fireItemLore)
			.build();

		var iceItemLore = config.getStringList("element-menu.ice-element.lore");
		var iceItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.ice-element.material")).orElse(XMaterial.HEART_OF_THE_SEA))
			.name(Utils.center(config.getString("element-menu.ice-element.name"), iceItemLore))
			.lore(iceItemLore)
			.build();

		var earthItemLore = config.getStringList("element-menu.earth-element.lore");
		var earthItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.earth-element.material")).orElse(XMaterial.PITCHER_POD))
			.name(Utils.center(config.getString("element-menu.earth-element.name"), earthItemLore))
			.lore(earthItemLore)
			.build();

		var airItemLore = config.getStringList("element-menu.airelement.lore");
		var airItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.air-element.material")).orElse(XMaterial.SNOWBALL))
			.name(Utils.center(config.getString("element-menu.air-element.name"), airItemLore))
			.lore(fireItemLore)
			.build();

		menu.pane.addItem(GuiItem.of(fireItem), 1, 1);
		menu.pane.addItem(GuiItem.of(iceItem), 3, 1);
		menu.pane.addItem(GuiItem.of(earthItem), 5, 1);
		menu.pane.addItem(GuiItem.of(airItem), 7, 1);
	}
}