package lunar.enchantments.shield;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lunar.template.ExtendedOffhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class ShieldBash extends ExtendedOffhandEnchantment {
	public ShieldBash() {
		super("shield_bash");
		this.setWeight(5);
		this.max_level = 5;
		this.min_level = 1;
		this.max_cost = new int[] { 16, 8 };
		this.min_cost = new int[] { 10, 5 };
		this.is_discoverable = true;
		this.is_tradeable = true;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {// 副手装盾
				event.setDamage(event.getDamage() + resolveEnchantmentValue());
			}
		}
	}

	// 根据附魔等级获取伤害加成
	@Override
	public double getEnchantmentValue(int level) {
		return level;
	}
}