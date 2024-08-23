package lunar.enchantments.mining;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import lib.crescent.item.ItemUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

public class Smelting extends ExtendedMainhandEnchantment {
	public Smelting() {
		super("smelting");
		this.display_name_style.setColor(FormattingCode.MINECOIN_COPPER);
		this.setWeight(1);
		this.max_level = 1;
		this.min_level = 1;
		this.max_cost = new int[] { 34, 12 };
		this.min_cost = new int[] { 30, 10 };
		setMainhandType(MainhandType.MINING);
		this.is_treasure = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)// 创造模式无效
			return;
		setTriggerEntity(player);
		if (resolveTrigger()) {
			Block block = event.getBlock();
			Collection<ItemStack> drops = block.getDrops(trigger_item);
			for (ItemStack item : drops) {
				ItemStack output = null;
				if ((output = ItemUtils.getFurnaceOutput(item)) == null)
					block.getWorld().dropItemNaturally(block.getLocation(), item);
				else
					block.getWorld().dropItemNaturally(block.getLocation(), output);
			}
			event.setDropItems(false);
		}
	}
}