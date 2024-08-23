package lunar.enchantments.shield;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedOffhandEnchantment;

//反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Reflection extends ExtendedOffhandEnchantment {
	public Reflection() {
		super("reflection");
		this.display_name_style.setColor("#5d0eb9");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 55, 15 };
		this.min_cost = new int[] { 50, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:mirror"));
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {// 副手装盾且有盾牌反射附魔才能返还攻击
				LivingEntity target = EntityUtils.getDamageSourceEntity(event.getDamager());
				target.damage(event.getDamage() * resolveEnchantmentValue(), event.getDamageSource());// 攻击者受到全额返还
				EntityUtils.spawnParticle(target, Particle.CRIT, 40, 2, 2, 2);
			}
		}
	}

	// 根据附魔等级设置反伤比例
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.30;
		case 2:
			return 0.60;
		case 3:
			return 1;
		}
		return 0;
	}
}