package me.despical.qgloria.elements;

import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.elements.base.Passive;
import me.despical.qgloria.user.User;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Barbarian extends Element {

	public Barbarian(User user) {
		super(user, 2);
		this.attack = 5;
		this.speed = -3;
		this.lavaDefense = -3;
	}

	@Override
	public void registerPassives() {
		var herbalRemedy = new Passive("herbal_remedy", 1, false);
		herbalRemedy.setInitializer(player -> plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			var location = player.getLocation();
			var crops = new ArrayList<Block>();

			for (final var block : getNearbyBlocks(location)) {
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
		}, 20L, 10L));

		addPassive(herbalRemedy);
	}

	private static final Set<String> FLOWERS = Set.of("DEAD_BUSH", "DANDELION", "POPPY", "BLUE_ORCHID", "ALLIUM", "AZURE_BLUET",
		"TULIP", "DAISY", "FLOWER", "LILY", "ROSE", "BLOSSOM", "MUSHROOM", "FUNGUS", "ROOTS", "SPROUTS");

	private List<Block> getNearbyBlocks(Location location) {
		var blocks = new ArrayList<Block>();

		for (int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; x++) {
			for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; y++) {
				for (int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}

		return blocks;
	}
}