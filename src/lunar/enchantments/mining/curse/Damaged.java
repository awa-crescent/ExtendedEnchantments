package lunar.enchantments.mining.curse;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

public class Damaged extends ExtendedMainhandEnchantment {
	public Damaged() {
		super("damaged");
		this.display_name_style.setColor(FormattingCode.DARK_GREY);
		this.setWeight(3);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 12, 11 };
		this.min_cost = new int[] { 10, 10 };
		setMainhandType(MainhandType.MINING);
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)// 创造模式无效
			return;
		setTriggerEntity(player);
		if (resolveTrigger())
			event.setCancelled(true);
	}

	// 根据附魔等级设置触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.05;
		case 2:
			return 0.10;
		case 3:
			return 0.20;
		}
		return 0;
	}
}