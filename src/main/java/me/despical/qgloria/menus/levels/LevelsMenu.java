package me.despical.qgloria.menus.levels;

import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.pane.PaginatedPane;
import me.despical.qgloria.Main;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 16.12.2023
 */
public class LevelsMenu {

	PaginatedPane paginatedPane;
	Player player;
	Main plugin;
	Gui gui;

	public LevelsMenu(Main plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.gui = new Gui(plugin, 6,"Seviye İlerlemeniz");
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.paginatedPane = new PaginatedPane(9, 6);
		this.gui.addPane(paginatedPane);

		var components = new LevelComponents();
		components.injectComponents(this);

		this.gui.show(player);
	}
}