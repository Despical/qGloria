package me.despical.battleacademy.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

	public static String center(String title, List<String> lore) {
		var j = lore.stream().map(Utils::lengthWithoutColors).max(Comparator.naturalOrder()).get();
		var spaces = j / 2 + lengthWithoutColors(title);

		return " ".repeat(Math.max(0, spaces)) + title;
	}

	public static int lengthWithoutColors(String message) {
		var pattern = Pattern.compile("#[a-fA-F0-9]{6}");
		var matcher = pattern.matcher(message);

		while (matcher.find()) {
			String hexCode = message.substring(matcher.start(), matcher.end());
			message = message.replace(hexCode,  "");
			matcher = pattern.matcher(message);
			message = message.replaceAll("(?i)" + '&' + "[0-9A-FK-ORX]", "");
		}

		return message.length();
	}

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