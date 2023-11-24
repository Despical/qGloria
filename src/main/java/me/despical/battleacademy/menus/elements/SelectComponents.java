package me.despical.battleacademy.menus.elements;

import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.inventoryframework.GuiItem;

public class SelectComponents {

	public void injectComponents(SelectMenu menu) {
		final var config = ConfigUtils.getConfig(menu.plugin, "menu");

		var fireItem = new ItemBuilder(XMaterial.CAMPFIRE)
			.name(config.getString("element-menu.fire-element.name"))
			.lore(config.getStringList("element-menu.fire-element.lore"))
			.build();

		menu.pane.addItem(GuiItem.of(fireItem), 1, 1);
	}
}