package lunar.enchantments.weapon;

import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Punishment extends ExtendedMainhandEnchantment {
	public Punishment() {
		super("punishment");
		this.display_name_style.setColor("#660033");
		this.setWeight(1);
		this.max_level = 5;
		this.min_level = 1;
		this.max_cost = new int[] { 24, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				double void_damage_percentage = resolveEnchantmentValue();
				damagee.damage(void_damage_percentage, DamageSource.builder(DamageType.GENERIC_KILL).build());// 虚空伤害（真伤）
				event.setDamage(event.getDamage() * (1 - void_damage_percentage));// 非真伤
			}
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.10;
		case 2:
			return 0.30;
		case 3:
			return 0.50;
		case 4:
			return 0.70;
		case 5:
			return 0.90;
		}
		return 0;
	}
}