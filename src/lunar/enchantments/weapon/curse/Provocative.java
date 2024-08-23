package lunar.enchantments.weapon.curse;

import java.util.Collection;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Golem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedMainhandEnchantment;

//挑衅：攻击敌人时周围的生物都会攻击你
public class Provocative extends ExtendedMainhandEnchantment {
	public Provocative() {
		super("provocative");
		this.display_name_style.setColor("#8b0101");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 12, 11 };
		this.min_cost = new int[] { 10, 10 };
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = true;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			this.setTriggerEntity(damager);
			if (resolveTrigger()) {
				Collection<Entity> entities = damagee.getNearbyEntities(resolveEnchantmentValue(), resolveEnchantmentValue(), resolveEnchantmentValue());
				for (Entity entity : entities)
					if (entity instanceof Monster || entity instanceof Golem) {
						((LivingEntity) entity).attack(damager);
						EntityUtils.spawnParticle(entity, Particle.ANGRY_VILLAGER, 5, 0.5, 1, 0.5);
					}
			}
		}
	}

	// 根据附魔等级不同获取挑衅范围
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 8;
		case 2:
			return 16;
		case 3:
			return 32;
		}
		return 0;
	}
}