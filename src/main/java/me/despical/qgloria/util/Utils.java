package me.despical.qgloria.util;

import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.elements.Karus;
import me.despical.qgloria.elements.Barbarian;
import me.despical.qgloria.elements.Kurian;
import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

	private Utils() {
	}

	public static String intToRoman(int i) {
		if (i == 5) return "V";
		return "I".repeat(i);
	}

	public static Element getElement(User user) {
		var element =  switch (user.getStat(StatsStorage.StatisticType.ELEMENT)) {
			case 1 -> new Kurian(user);
			case 2 -> new Barbarian(user);
			case 3 -> new Karus(user);
			default -> throw new IllegalStateException("Unexpected value: " + user.getStat(StatsStorage.StatisticType.ELEMENT));
		};

		element.initialize();
		return element;
	}

	public static String getElementName(User user) {
		return switch (user.getStat(StatsStorage.StatisticType.ELEMENT)) {
			case 1 -> "Kurian";
			case 2 -> "Barbarian";
			case 3 -> "Karus";
			default -> "Henüz Seçilmedi";
		};
	}

	public static Element getElementFromId(User user, int id) {
		var element = switch (id) {
			case 1 -> new Kurian(user);
			case 2 -> new Barbarian(user);
			case 3 -> new Karus(user);
			default -> throw new IllegalStateException("Unexpected value: " + user.getStat(StatsStorage.StatisticType.ELEMENT));
		};

		element.initialize();
		return element;
	}

	public static String center(String title, List<String> lore) {
		var j = lore.stream().map(Utils::lengthWithoutColors).max(Comparator.naturalOrder()).get();
		var spaces = j / 2 + lengthWithoutColors(title) - 2;

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