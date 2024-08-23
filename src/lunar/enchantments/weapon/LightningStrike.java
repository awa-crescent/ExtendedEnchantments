package lunar.enchantments.weapon;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class LightningStrike extends ExtendedMainhandEnchantment {
	public LightningStrike() {
		super("lightning_strike");
		this.display_name_style.setColor(FormattingCode.YELLOW);
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 35, 15 };
		this.min_cost = new int[] { 30, 10 };
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity entity) {// 造成伤害的是玩家，受到伤害的是实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				entity.getWorld().strikeLightning(entity.getLocation());
			}
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.05;
		case 2:
			return 0.15;
		case 3:
			return 0.30;
		}
		return 0;
	}
}