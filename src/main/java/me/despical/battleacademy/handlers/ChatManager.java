package me.despical.battleacademy.handlers;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.user.User;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.util.Strings;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ChatManager {

	private final FileConfiguration config;

	public ChatManager(Main plugin) {
		this.config = ConfigUtils.getConfig(plugin, "messages");
	}

	public String message(final String path) {
		return rawMessage(this.config.getString(path));
	}

	public String message(final String path, final User user) {
		String message = this.message(path);

		message = message.replace("%player%", user.getName());
		message = message.replace("%level%", Integer.toString(user.getLevel()));
		return message;
	}

	public ConfigurationSection getConfigurationSection(final String path) {
		return config.getConfigurationSection(path);
	}

	public String rawMessage(final String message) {
		return Strings.format(message);
	}

	public List<String> getStringList(final String path) {
		return this.config.getStringList(path);
	}
}