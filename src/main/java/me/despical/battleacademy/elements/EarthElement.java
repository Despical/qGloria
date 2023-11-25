package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.user.User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class EarthElement extends Element {

	public EarthElement(User user) {
		super(user, 3);
		this.lavaDefense = 2;
		this.defense = 5;
		this.fallDamage = 10;
		this.speed = -5;
	}

	@Override
	public void registerPassives() {

		var AutoSmelt = new Passive("auto_smelt", 5, false);

		AutoSmelt.setListener(new Listener() {


			@EventHandler
			public void AutoSmelt(BlockBreakEvent e) {
				Player player = (Player) e.getPlayer();
				Block block = (Block) e.getBlock();

				Material copperDrop = Material.COPPER_INGOT;
				Material ironDrop = Material.IRON_INGOT;
				Material goldDrop = Material.GOLD_INGOT;
				Material debrisDrop = Material.NETHERITE_SCRAP;
				boolean Pickaxe1 = player.getItemInHand().getType().equals(Material.STONE_PICKAXE);
				boolean Pickaxe2 = player.getItemInHand().getType().equals(Material.IRON_PICKAXE);
				boolean Pickaxe3 = player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE);
				boolean Pickaxe4 = player.getItemInHand().getType().equals(Material.NETHERITE_PICKAXE);

				double CopperXP = plugin.getConfig().getDouble("element-settings.earth.passive.mining-power-setting.copper-xp");
				double IronXP = plugin.getConfig().getDouble("element-settings.earth.passive.mining-power-setting.iron-xp");
				double GoldXP = plugin.getConfig().getDouble("element-settings.earth.passive.mining-power-setting.gold-xp");
				double ScrapXP = plugin.getConfig().getDouble("element-settings.earth.passive.mining-power-setting.debris-xp");

				if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power")) {
					switch (e.getBlock().getType()) {
						case COPPER_ORE, DEEPSLATE_COPPER_ORE:
							if (Pickaxe1 || Pickaxe2 || Pickaxe3 || Pickaxe4) {
								e.setCancelled(true);
								block.setType(Material.AIR);
								player.giveExp((int) CopperXP);
								if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
									block.getWorld().dropItem(player.getLocation(), new ItemStack(copperDrop));
								} else {
									player.getInventory().addItem(new ItemStack(copperDrop));
								}
							}
						case IRON_ORE, DEEPSLATE_IRON_ORE:
							if (Pickaxe1 || Pickaxe2 || Pickaxe3 || Pickaxe4) {
								e.setCancelled(true);
								block.setType(Material.AIR);
								player.giveExp((int) IronXP);
								if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
									block.getWorld().dropItem(player.getLocation(), new ItemStack(ironDrop));
								} else {
									player.getInventory().addItem(new ItemStack(ironDrop));
								}
							}
						case GOLD_ORE, DEEPSLATE_GOLD_ORE:
							if (Pickaxe2 || Pickaxe3 || Pickaxe4) {
								e.setCancelled(true);
								block.setType(Material.AIR);
								player.giveExp((int) GoldXP);
								if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
									block.getWorld().dropItem(player.getLocation(), new ItemStack(goldDrop));
								} else {
									player.getInventory().addItem(new ItemStack(goldDrop));
								}
							}
						case ANCIENT_DEBRIS:
							if (Pickaxe3 || Pickaxe4) {
								e.setCancelled(true);
								block.setType(Material.AIR);
								player.giveExp((int) ScrapXP);
								if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
									block.getWorld().dropItem(player.getLocation(), new ItemStack(debrisDrop));
								} else {
									player.getInventory().addItem(new ItemStack(debrisDrop));
								}
							}

					}
				}


			}
		});

	}
}