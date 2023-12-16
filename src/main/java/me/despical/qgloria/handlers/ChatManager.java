package me.despical.qgloria.handlers;

import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.util.Strings;
import me.despical.qgloria.Main;
import me.despical.qgloria.user.User;
import org.bukkit.configuration.file.FileConfiguration;

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

	public String rawMessage(final String message) {
		return Strings.format(message);
	}
}