package me.despical.qgloria.handlers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.despical.qgloria.Main;
import me.despical.qgloria.util.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 16.12.2023
 */
public class PlaceholderManager extends PlaceholderExpansion {

	private final Main plugin;

	public PlaceholderManager(Main plugin) {
		this.plugin = plugin;

		register();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@NotNull
	@Override
	public String getIdentifier() {
		return "qgloria";
	}

	@NotNull
	@Override
	public String getAuthor() {
		return "Despical";
	}

	@NotNull
	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	public String onPlaceholderRequest(Player player, @NotNull String id) {
		if (player == null) return null;

		final var user = plugin.getUserManager().getUser(player);

		return switch (id.toLowerCase()) {
			case "sınıf" -> Utils.getElementName(user);
			case "savaş_gücü" -> String.format("%.2f".formatted(plugin.getCalculator().calculatePlayerNumber(player, false).result()));
			default -> null;
		};
	}
}