package me.despical.battleacademy.elements.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.despical.battleacademy.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class Passive {

	private final String passiveName;
	private final int enableAt;
	private final boolean enabled;

	@Getter
	@Setter
	private Listener listener;

	@Getter
	@Setter
	private Consumer<Player> initializer;

	void registerEvents(Main plugin) {
		if (listener != null) plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}
}