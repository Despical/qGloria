package me.despical.qgloria.menus;

import me.despical.qgloria.Main;
import me.despical.commons.compat.XMaterial;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;

public class SelectMenu {

	StaticPane pane;
	Player player;
	Main plugin;
	Gui gui;

	public SelectMenu(Main plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.gui = new Gui(plugin, 3,"Sınıf Seçin");
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.pane = new StaticPane(9,3);
		this.pane.fillWith(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());
		this.gui.setOnClose(event -> plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
			var element = plugin.getUserManager().getUser(player).getElement();

			if (element == null || element.getId() == 0) {
				gui.show(player);
			}
		}, 2L));

		var components = new SelectComponents();
		components.injectComponents(this);

		this.gui.addPane(pane);
		this.gui.show(player);
	}
}