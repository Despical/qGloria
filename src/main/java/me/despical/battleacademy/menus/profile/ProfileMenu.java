package me.despical.battleacademy.menus.profile;

import me.despical.battleacademy.Main;
import me.despical.commons.compat.XMaterial;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.GuiItem;
import me.despical.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;

public class ProfileMenu {

	final StaticPane pane;
	final Gui gui;
	final Player player;
	final Main plugin;

	public ProfileMenu(Main plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.gui = new Gui(plugin, 6, "My Profile");
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.pane = new StaticPane(9, 6);
		this.pane.fillHorizontallyWith(GuiItem.of(XMaterial.ORANGE_STAINED_GLASS_PANE.parseItem()), 1);

		var components = new ProfileComponents();
		components.injectComponents(this);

		this.gui.addPane(pane);
		this.gui.show(player);
	}
}