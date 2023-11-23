package me.despical.battleacademy.commands;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

public class Commands {

	private final Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommandFramework().registerCommands(this);
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
	}
}