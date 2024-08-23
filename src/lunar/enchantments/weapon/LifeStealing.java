package lunar.enchantments.weapon;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class LifeStealing extends ExtendedMainhandEnchantment {
	public LifeStealing() {
		super("life_stealing");
		this.display_name_style.setColor("#247f00");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 45, 15 };
		this.min_cost = new int[] { 40, 12 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger())
				EntityUtils.cureEntity(damager, event.getFinalDamage() * resolveEnchantmentValue());
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.20;
		case 2:
			return 0.30;
		case 3:
			return 0.50;
		}
		return 0;
	}
}