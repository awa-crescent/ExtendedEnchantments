package lunar.enchantments.mining;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

public class Collect extends ExtendedMainhandEnchantment {
	public Collect() {
		super("collect");
		this.display_name_style.setColor(FormattingCode.GREEN);
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 20, 12 };
		this.min_cost = new int[] { 15, 10 };
		setMainhandType(MainhandType.MINING);
		this.is_treasure = true;
		this.is_discoverable = true;
		this.is_tradeable = true;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)// 创造模式无效
			return;
		setTriggerEntity(player);
		if (resolveTrigger()) {
			Block block = event.getBlock();
			BlockData block_data = block.getBlockData();
			Material material = block.getType();
			if (block_data instanceof Leaves)
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE, 1));
			else if (material == Material.SHORT_GRASS || material == Material.TALL_GRASS)
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SWEET_BERRIES, 1));
		}
	}

	@Override
	public double getTriggerChance(int level) {
		return 0.1 * level;
	}
}