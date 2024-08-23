package lunar.enchantments.weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Critical extends ExtendedMainhandEnchantment {
	public Critical() {
		super("critical");
		this.setWeight(3);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 25, 15 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:counterstrike"));
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				event.setDamage(event.getDamage() * 1.5);// 暴击则造成1.5倍伤害
				EntityUtils.spawnParticle(damagee, Particle.CRIT, 80, 0.5, 1, 0.5);
			}
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.10;
		case 2:
			return 0.15;
		case 3:
			return 0.25;
		}
		return 0;
	}
}