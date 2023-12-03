package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.user.User;
import me.despical.commons.compat.XMaterial;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EarthElement extends Element {

	public EarthElement(User user) {
		super(user, 3);
		this.lavaDefense = 2;
		this.defense = 5;
		this.fallDamage = 10;
		this.speed = -5;
	}

	@Override
	public List<Enchantment> getEnchantments() {
		return null;
	}

	@Override
	public void registerPassives() {
		var autoSmelt = new Passive("auto_smelt", 5, false);
		autoSmelt.setListener(new Listener() {

			@EventHandler
			public void AutoSmelt(BlockBreakEvent e) {
				if (!isPassiveEnabled(autoSmelt)) return;

				Player player = e.getPlayer();
				Block block = e.getBlock();

				var itemInHand = player.getItemInHand().getType();
				var Pickaxe1 = itemInHand == Material.STONE_PICKAXE;
				var Pickaxe2 = itemInHand == Material.IRON_PICKAXE;
				var Pickaxe3 = itemInHand == Material.DIAMOND_PICKAXE;
				var Pickaxe4 = itemInHand == Material.NETHERITE_PICKAXE;

				var copperXP = plugin.getConfig().getInt("element-settings.earth.passive.mining-power-setting.copper-xp");
				var ironXP = plugin.getConfig().getInt("element-settings.earth.passive.mining-power-setting.iron-xp");
				var goldXP = plugin.getConfig().getInt("element-settings.earth.passive.mining-power-setting.gold-xp");
				var scrapXP = plugin.getConfig().getInt("element-settings.earth.passive.mining-power-setting.debris-xp");

				if (!plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power")) return;

				switch (e.getBlock().getType()) {
					case COPPER_ORE, DEEPSLATE_COPPER_ORE:
						if (Pickaxe1 || Pickaxe2 || Pickaxe3 || Pickaxe4) {
							e.setCancelled(true);
							block.setType(Material.AIR);
							player.giveExp(copperXP);

							if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
								block.getWorld().dropItem(player.getLocation(), XMaterial.COPPER_INGOT.parseItem());
							} else {
								player.getInventory().addItem(XMaterial.COPPER_INGOT.parseItem());
							}
						}

					case IRON_ORE, DEEPSLATE_IRON_ORE:
						if (Pickaxe1 || Pickaxe2 || Pickaxe3 || Pickaxe4) {
							e.setCancelled(true);
							block.setType(Material.AIR);
							player.giveExp(ironXP);

							if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
								block.getWorld().dropItem(player.getLocation(), XMaterial.IRON_INGOT.parseItem());
							} else {
								player.getInventory().addItem(XMaterial.IRON_INGOT.parseItem());
							}
						}

					case GOLD_ORE, DEEPSLATE_GOLD_ORE:
						if (Pickaxe2 || Pickaxe3 || Pickaxe4) {
							e.setCancelled(true);
							block.setType(Material.AIR);
							player.giveExp(goldXP);

							if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
								block.getWorld().dropItem(player.getLocation(), XMaterial.GOLD_INGOT.parseItem());
							} else {
								player.getInventory().addItem(XMaterial.GOLD_INGOT.parseItem());
							}
						}

					case ANCIENT_DEBRIS:
						if (Pickaxe3 || Pickaxe4) {
							e.setCancelled(true);
							block.setType(Material.AIR);
							player.giveExp(scrapXP);

							if (plugin.getConfig().getBoolean("element-settings.earth.passive.mining-power-setting.drop")) {
								block.getWorld().dropItem(player.getLocation(), XMaterial.NETHERITE_SCRAP.parseItem());
							} else {
								player.getInventory().addItem(XMaterial.NETHERITE_SCRAP.parseItem());
							}
						}
				}
			}
		});

		var fastGrow = new Passive("fast_grow", 8, false);
		fastGrow.setListener(new Listener() {

			private List<Block> crops = new ArrayList<>();

			@EventHandler
			public void onBlockPlace(BlockPlaceEvent event) {
				if (!isPassiveEnabled(fastGrow)) return;

				switch (event.getBlock().getType()) {
					case WHEAT, BEETROOTS, PUMPKIN_STEM, MELON_STEM:
						crops.add(event.getBlock());
				}
			}

			@EventHandler
			public void onBlockBreak(BlockBreakEvent event) {
				if (!isPassiveEnabled(fastGrow)) return;

				switch (event.getBlock().getType()) {
					case WHEAT, BEETROOTS, PUMPKIN_STEM, MELON_STEM:
						crops.remove(event.getBlock());
				}
			}

			@EventHandler
			public void fastGrow(BlockGrowEvent event) {
				if (!isPassiveEnabled(fastGrow)) return;

				var block = event.getBlock();

				if (!crops.contains(block)) return;

				var blockData = block.getBlockData();

				if (blockData instanceof Ageable ageable) {
					event.setCancelled(true);

					ageable.setAge(Math.min(ageable.getAge() + 2, 7));
					block.setBlockData(ageable);

					var loc = block.getLocation();
					loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 3);
				}
			}
		});

		var herbalRemedy = new Passive("herbal_remedy", 1, false);
		herbalRemedy.setInitializer(player -> {
			plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
				var location = player.getLocation();
				var crops = new ArrayList<Block>();

				for (final var block : getNearbyBlocks(location, 2)) {
					if (FLOWERS.contains(block.getType().name()))
						crops.add(block);
				}

				int i = 1;

				for (var block : crops) {
					if (i++ <= 5) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, crops.size() - 1, false, false, false));
					}

					new BukkitRunnable() {
						final Location startLoc = block.getLocation().clone().add(.5, .2, .5);
						final Location particleLoc = startLoc.clone();
						final World world = startLoc.getWorld();
						int ticks = 0, beamLength = 0;
						final int maxBeamLength = 7;

						public void run() {
							ticks++;

								Location targetLoc = player.getLocation().clone().add(0, .5, 0);
								Vector vecOffset = targetLoc.clone().subtract(particleLoc).toVector().normalize();

								if (beamLength++ >= maxBeamLength) {
									this.cancel();
									return;
								}

								particleLoc.add(vecOffset);

								world.spawnParticle(Particle.VILLAGER_HAPPY, particleLoc.clone().subtract(vecOffset), 0);
						}
					}.runTaskTimer(plugin, 0, 1);

				}

				crops.clear();
			}, 20L, 10L);
		});

		addPassive(autoSmelt, fastGrow, herbalRemedy);
	}

	private static final Set<String> FLOWERS = Set.of("DEAD_BUSH", "DANDELION", "POPPY", "BLUE_ORCHID", "ALLIUM", "AZURE_BLUET",
		"TULIP", "DAISY", "FLOWER", "LILY", "ROSE", "BLOSSOM", "MUSHROOM", "FUNGUS", "ROOTS", "SPROUTS");

	private List<Block> getNearbyBlocks(Location location, int radius) {
		var blocks = new ArrayList<Block>();

		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}

		return blocks;
	}
}