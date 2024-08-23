package lunar.enchantments.shield;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lunar.template.ExtendedOffhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Barrier extends ExtendedOffhandEnchantment {
	public Barrier() {
		super("barrier");
		this.display_name_style.setColor("#004374");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 45, 15 };
		this.min_cost = new int[] { 40, 10 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) // 副手装盾且有盾牌反射附魔才能返还攻击
				event.setDamage(event.getDamage() * (1 - resolveEnchantmentValue()));
		}
	}

	// 根据附魔等级设置反伤比例
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.10;
		case 2:
			return 0.20;
		case 3:
			return 0.30;
		}
		return 0;
	}
}