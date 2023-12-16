package me.despical.qgloria.commands;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.Completer;
import me.despical.commons.string.StringMatcher;
import me.despical.qgloria.Main;
import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.item.RuneStone;
import me.despical.qgloria.menus.SelectMenu;
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
		name = "qgloria"
	)
	public void mainCommand(CommandArguments arguments) {
	}

	@Command(
		name = "qgloria.runestone",
		permission = "qgloria.runestone"
	)
	public void runeStoneCommand(CommandArguments arguments) {
		Player player = arguments.getSender();
		player.getInventory().addItem(RuneStone.RUNE_STONE);
	}

	@Command(
		name = "qgloria.reset",
		permission = "qgloria.reset",
		min = 1,
		senderType = Command.SenderType.PLAYER
	)
	public void resetCommand(CommandArguments arguments) {
		String name = arguments.getArgument(0);
		Player target = plugin.getServer().getPlayerExact(name);

		if (target == null) {
			arguments.sendMessage(plugin.getChatManager().message("messages.player-not-found"));
			return;
		}

		var user = plugin.getUserManager().getUser(target);
		user.setStat(StatsStorage.StatisticType.ELEMENT, 0);

		arguments.sendMessage(plugin.getChatManager().rawMessage("&a" + name + " adlı oyuncu artık yeniden bir sınıf seçebilir."));
	}

	@Command(
		name = "qgloria.select",
		senderType = Command.SenderType.PLAYER
	)
	public void selectCommands(CommandArguments arguments) {
		new SelectMenu(plugin, arguments.getSender());
	}

	@Completer(
		name = "qgloria",
		permission = "qgloria.admin"
	)
	public List<String> completer(CommandArguments arguments) {
		List<String> completions = new ArrayList<>(), commands = plugin.getCommandFramework().getSubCommands().stream().map(cmd -> cmd.name().split("\\.")[1]).toList();

		StringUtil.copyPartialMatches(arguments.getArgument(0), commands, completions);
		return completions;
	}
}