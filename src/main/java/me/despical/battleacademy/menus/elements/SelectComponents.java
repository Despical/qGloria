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
		var fireItem = new ItemBuilder(XMaterial.matchXMaterial(config.getString("element-menu.fire-element.material")).orElse(XMaterial.CAMPFIRE))
			.name(Utils.center(config.getString("element-menu.fire-element.name"), fireItemLore))
			.lore(fireItemLore)
			.build();

		menu.pane.addItem(GuiItem.of(fireItem), 1, 1);
	}
}