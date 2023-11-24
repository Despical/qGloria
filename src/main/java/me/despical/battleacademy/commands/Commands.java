package me.despical.battleacademy.commands;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.menus.profile.ProfileMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.Completer;
import me.despical.commons.string.StringMatcher;

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
		name = "battleacademy.stats",
		senderType = Command.SenderType.PLAYER
	)
	public void statsCommand(CommandArguments arguments) {
		final var user = plugin.getUserManager().getUser(arguments.getSender());

		user.sendRawMessage("Level: %d\nXP: %d", user.getStat(StatsStorage.StatisticType.LEVEL), user.getStat(StatsStorage.StatisticType.XP));
		user.sendRawMessage("Kills: " + user.getStat(StatsStorage.StatisticType.KILLS));
		user.sendRawMessage("Death: " + user.getStat(StatsStorage.StatisticType.DEATHS));
		user.sendRawMessage("Taken Damage: " + user.getStat(StatsStorage.StatisticType.DAMAGE_TAKEN));
	}

	@Command(
		name = "battleacademy.profile",
		senderType = Command.SenderType.PLAYER
	)
	public void profileCommand(CommandArguments arguments) {
		new ProfileMenu(plugin, arguments.getSender());
	}

	@Completer(
		name = "battleacademy"
	)
	public List<String> completer(CommandArguments arguments) {
		return List.of("stats", "profile");
	}
}