package me.despical.battleacademy.elemental;

import me.despical.battleacademy.Main;
import me.despical.commons.compat.XMaterial;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.GuiItem;
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
		this.gui = new Gui(plugin, 3,"Element Select");
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.pane = new StaticPane(9,3);
		this.pane.fillWith(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());



		var components = new SelectComponents();
		components.injectComponents(this);

		this.gui.addPane(pane);
		this.gui.show(player);
	}
}
