package me.despical.qgloria.item;

import io.th0rgal.oraxen.api.OraxenItems;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.commons.util.Strings;
import me.despical.qgloria.Main;
import me.despical.qgloria.enchantments.base.CustomEnchantment;
import me.despical.qgloria.enchantments.base.EnchantmentManager;
import me.despical.qgloria.util.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public class RuneStone implements Listener {

	private static final Main plugin = JavaPlugin.getPlugin(Main.class);
	public static final ItemStack RUNE_STONE = new RuneStone(plugin).runeStone.clone();

	private final ItemStack runeStone;

	private RuneStone(Main plugin) {
		final var config = ConfigUtils.getConfig(plugin, "items");

		this.runeStone = OraxenItems.getItemById(config.getString("rune-stone.oraxen-id")).build();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (!event.getAction().isRightClick()) return;
		if (event.getItem() == null) return;

		ItemStack item = event.getItem();

		if (!RUNE_STONE.equals(item)) return;

		Player player = event.getPlayer();
		item.setAmount(item.getAmount() - 1);

		ItemStack book = new ItemBuilder(Material.ENCHANTED_BOOK).build();
		EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
		CustomEnchantment enchantment = (CustomEnchantment) EnchantmentManager.RANDOM_ENCHANTMENT.get();
		int level = enchantment.getRandomLevel();
		bookMeta.addStoredEnchant(EnchantmentManager.ATTACK_POWER, level, true);
		bookMeta.setLore(Stream.of("&7" + enchantment.getName() + " " + Utils.intToRoman(level)).map(Strings::format).toList());
		book.setItemMeta(bookMeta);

		player.getInventory().addItem(book);
	}
}