package lunar.enchantments.armor.curse;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.LightningStrikeEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Conductive extends ExtendedArmorEnchantment {
	private boolean has_generated_lightning = false;

	public Conductive() {
		super("conductive");
		this.level_type = ArmorLevelType.MAX;
		this.display_name_style.setColor(FormattingCode.YELLOW);
		this.setWeight(1);
		this.max_level = 2;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:charged"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onLightningStrikeEvent(LightningStrikeEvent event) {
		Collection<Entity> entities = event.getLightning().getNearbyEntities(32, 16, 32);
		for (Entity entity : entities) {// 受到伤害的是人形实体
			if (!(entity instanceof LivingEntity))
				continue;
			setTriggerEntity((LivingEntity) entity);
			if (resolveTrigger()) {
				if (has_generated_lightning) {
					has_generated_lightning = false;
					return;
				} else {
					has_generated_lightning = true;
					// 导电所产生的闪电会记录在event_lightning_uuid中，避免递归调用导致栈溢出
					event.getWorld().strikeLightning(entity.getLocation());
					EntityUtils.spawnParticle(entity, Particle.ELECTRIC_SPARK, 80, 1, 1, 1);
					event.setCancelled(true);
				}
			}
		}
	}

	@Override
	public double getTriggerChance(int max_level) {
		return 0.5 * max_level;
	}
}