package me.despical.battleacademy.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Utils {

	public static boolean isCriticalHit(Player player) {
		return
			player.getFallDistance() > 0.0F &&
				!player.isOnGround() &&
				!player.isInsideVehicle() &&
				!player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
				player.getLocation().getBlock().getType() != Material.LADDER &&
				player.getLocation().getBlock().getType() != Material.VINE;
	}
}