package me.despical.qgloria.item;

import me.despical.commons.util.Strings;
import me.despical.qgloria.Main;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.qgloria.enchantments.base.CustomEnchantment;
import me.despical.qgloria.enchantments.base.EnchantmentManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Stream;

public class RuneStone implements Listener {

	private static final Main plugin = JavaPlugin.getPlugin(Main.class);
	public static final ItemStack RUNE_STONE = new RuneStone(plugin).runeStone;

	private final ItemStack runeStone;

	private RuneStone(Main plugin) {
		final FileConfiguration config = ConfigUtils.getConfig(plugin, "items");

		this.runeStone = new ItemBuilder(XMaterial.matchXMaterial(config.getString("rune-stone.material")).orElse(XMaterial.PAPER))
			.name(config.getString("rune-stone.name"))
			.lore(config.getStringList("rune-stone.lore"))
			.enchantment(Enchantment.DURABILITY)
			.flag(ItemFlag.HIDE_ENCHANTS)
			.build();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (!event.getAction().isRightClick()) return;
		if (event.getItem() == null) return;

		ItemStack item = event.getItem();

		if (!RUNE_STONE.equals(item)) return;

		Player player = event.getPlayer();

		player.getInventory().remove(RUNE_STONE);

		ItemStack book = new ItemBuilder(Material.ENCHANTED_BOOK).build();
		EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
		CustomEnchantment enchantment = (CustomEnchantment) EnchantmentManager.RANDOM_ENCHANTMENT.get();
		int level = enchantment.getRandomLevel();
		bookMeta.addStoredEnchant(EnchantmentManager.ATTACK_POWER, level, true);
		bookMeta.setLore(Stream.of("&7" + enchantment.getName() + " " + level).map(Strings::format).toList());
		book.setItemMeta(bookMeta);

		player.getLocation().getWorld().dropItemNaturally(player.getLocation(), book);
	}


	private boolean checkLevels(final Player player, final AnvilInventory anvil) {
//		final EnchantmentMerger merger = getMerger(anvil);
//		if (player.getLevel() >= merger.getCost()) {
//			player.setLevel(player.getLevel() - merger.getCost());
//			return true;
//		}
//		return false;
		return true;
	}

	private ItemStack select(final ItemStack first, final ItemStack second, final boolean result) {
		return (!isBook(first) || isBook(second)) == result ? first : second;
	}

	private boolean isBook(final ItemStack item) {
		return !isPresent(item) || item.getType() == Material.BOOK || item.getType() == Material.ENCHANTED_BOOK;
	}

	private boolean isSingle(final ItemStack item) {
		return isPresent(item) && item.getAmount() == 1;
	}

	private boolean isPresent(final ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
}