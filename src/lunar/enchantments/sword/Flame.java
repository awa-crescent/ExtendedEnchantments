package lunar.enchantments.sword;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Flame extends ExtendedMainhandEnchantment {
	public Flame() {
		super("flame");
		this.setMainhandType(MainhandType.SWORD);
		this.display_name_style.setColor(FormattingCode.GOLD);
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 34, 17 };
		this.min_cost = new int[] { 30, 15 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				List<Entity> nearby_entities = damagee.getNearbyEntities(resolveEnchantmentValue(0), resolveEnchantmentValue(0), resolveEnchantmentValue(0));
				nearby_entities.add(damagee);
				for (Entity entity : nearby_entities)
					if (entity != damager && entity instanceof LivingEntity target) {
						target.setFireTicks((int) (resolveEnchantmentValue(2) * 20));
						target.damage(resolveEnchantmentValue(1));
						target.playHurtAnimation(0);
						EntityUtils.spawnParticle(target, Particle.LAVA, 80, 1, 1, 1);
					}
				event.setCancelled(true);
			}
		}
	}

	// 数组成员：范围、一次性伤害、燃烧时长（秒）
	@Override
	public double[] getEnchantmentValues(int level) {
		double[] arr = null;
		switch (level) {
		case 1:
			arr = new double[] { 2, 4, 5 };
			break;
		case 2:
			arr = new double[] { 3, 5, 5 };
			break;
		case 3:
			arr = new double[] { 4, 6, 10 };
			break;
		}
		return arr;
	}
}
