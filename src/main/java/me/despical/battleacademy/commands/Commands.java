package me.despical.battleacademy.commands;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.enchantments.base.EnchantmentManager;
import me.despical.battleacademy.menus.elements.SelectMenu;
import me.despical.battleacademy.menus.profile.ProfileMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.Completer;
import me.despical.commons.string.StringMatcher;
import me.despical.commons.util.Strings;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Commands {

	private final Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommandFramework().registerCommands(this);
		this.plugin.getCommandFramework().setMatchFunction(arguments -> {
			if (arguments.isArgumentsEmpty()) return false;

			String label = arguments.getLabel(), arg = arguments.getArgument(0);

			var matches = StringMatcher.match(arg, plugin.getCommandFramework().getSubCommands().stream().map(cmd -> cmd.name().replace(label + ".", "")).collect(Collectors.toList()));

			if (!matches.isEmpty()) {
				arguments.sendMessage(plugin.getChatManager().message("commands.did-you-mean").replace("%command%", label + " " + matches.get(0).getMatch()));
				return true;
			}

			return false;
		});
	}

	@Command(
		name = "battleacademy"
	)
	public void mainCommand(CommandArguments arguments) {
		arguments.sendMessage("main command");
	}

	@Command(
		name = "battleacademy.enchantment",
		min = 1
	)
	public void enchantCommand(CommandArguments arguments) {
		Player player = arguments.getSender();

		switch (arguments.getArgumentAsInt(0)) {
			case 1 -> {
				var item = player.getItemInHand();
				var meta = item.getItemMeta();
				meta.setLore(List.of(Strings.format("&7Flame Arrows I")));
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(EnchantmentManager.FLAME_ARROWS, 1);

				player.sendMessage("Flame Arrows added!");
			}

			case 2 -> {
				var item = player.getInventory().getBoots();
				var meta = item.getItemMeta();
				meta.setLore(List.of(Strings.format("&7Quick Feet I")));
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(EnchantmentManager.QUICK_FEET, 1);

				player.sendMessage("Quick Feet added!");
			}

			case 3 -> {
				var item = player.getItemInHand();
				var meta = item.getItemMeta();
				meta.setLore(List.of(Strings.format("&7Rising Arrows I")));
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(EnchantmentManager.RISING_ARROWS, 1);

				player.sendMessage("Rising Arrows added!");
			}

			case 4 -> {
				var item = player.getItemInHand();
				var meta = item.getItemMeta();
				meta.setLore(List.of(Strings.format("&7Flame Circle I")));
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(EnchantmentManager.FLAME_CIRCLE, 1);

				player.sendMessage("Flame Circle added!");
			}
		}
	}

	@Command(
		name = "battleacademy.stats",
		senderType = Command.SenderType.PLAYER
	)
	public void statsCommand(CommandArguments arguments) {
		final var user = plugin.getUserManager().getUser(arguments.getSender());

		user.sendRawMessage("Level: %d\nXP: %d", user.getStat(StatsStorage.StatisticType.LEVEL), user.getStat(StatsStorage.StatisticType.XP));
		user.sendRawMessage("Kills: " + user.getStat(StatsStorage.StatisticType.KILLS));
		user.sendRawMessage("Death: " + user.getStat(StatsStorage.StatisticType.DEATHS));
		user.sendRawMessage("Element: " + user.getStat(StatsStorage.StatisticType.ELEMENT));
	}

	@Command(
		name = "battleacademy.profile",
		senderType = Command.SenderType.PLAYER
	)
	public void profileCommand(CommandArguments arguments) {
		new ProfileMenu(plugin, arguments.getSender());
	}

	@Command(
		name = "battleacademy.select",
		senderType = Command.SenderType.PLAYER
	)
	public void selectCommands(CommandArguments arguments) {
		new SelectMenu(plugin, arguments.getSender());
	}

	@Completer(
		name = "battleacademy"
	)
	public List<String> completer(CommandArguments arguments) {
		List<String> completions = new ArrayList<>(), commands = plugin.getCommandFramework().getSubCommands().stream().map(cmd -> cmd.name().split("\\.")[1]).toList();

		StringUtil.copyPartialMatches(arguments.getArgument(0), commands, completions);
		return completions;
	}
}