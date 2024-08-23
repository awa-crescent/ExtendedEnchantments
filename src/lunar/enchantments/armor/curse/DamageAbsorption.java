package lunar.enchantments.armor.curse;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//伤害吸收:当你附近范围内有实体受伤时，你会根据附魔等级分摊不同权重的伤害
public class DamageAbsorption extends ExtendedArmorEnchantment {
	private boolean damage_resolved = false;

	public DamageAbsorption() {
		super("damage_absorption");
		this.level_type = ArmorLevelType.MAX;
		this.display_name_style.setColor(FormattingCode.RED);
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 42, 11 };
		this.min_cost = new int[] { 40, 10 };
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (damage_resolved) {
			damage_resolved = false;
			return;
		}
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			Collection<Entity> nearby_entities = damagee.getNearbyEntities(8, 8, 8);
			ArrayList<LivingEntity> entities_should_be_damaged = new ArrayList<>();
			ArrayList<Integer> weights = new ArrayList<>();
			int total_weight = 0;
			setTriggerEntity(damagee);
			if (resolveTrigger()) {
				entities_should_be_damaged.add(damagee);
				int weight = (int) resolveEnchantmentValue();
				weights.add(weight);
				total_weight += weight;
			} else {
				entities_should_be_damaged.add(damagee);
				weights.add(1);
				total_weight += 1;
			}
			for (Entity entity : nearby_entities)
				if (entity instanceof LivingEntity nearby_entity) {
					setTriggerEntity(nearby_entity);
					if (resolveTrigger()) {
						entities_should_be_damaged.add(nearby_entity);
						int weight = (int) resolveEnchantmentValue();
						weights.add(weight);
						total_weight += weight;
					}
				}
			double average_damage = event.getDamage() / total_weight;
			for (int idx = 0; idx < entities_should_be_damaged.size(); ++idx) {
				LivingEntity entity_to_be_damaged = entities_should_be_damaged.get(idx);
				damage_resolved = true;
				entity_to_be_damaged.damage(average_damage * weights.get(idx), event.getDamageSource());
				if (total_weight > 1)
					EntityUtils.spawnParticle(entity_to_be_damaged, Particle.DAMAGE_INDICATOR, 10, 0.5, 1, 0.5);
			}
		}
	}

	// 根据附魔等级设置权重
	@Override
	public double getEnchantmentValue(int max_level) {
		return max_level;
	}
}