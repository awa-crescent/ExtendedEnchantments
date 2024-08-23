package lunar.enchantments.weapon;

import java.util.UUID;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.TwoKeysHashMap;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class BloodPact extends ExtendedMainhandEnchantment {
	private TwoKeysHashMap<UUID, UUID, Double> entity_damage_records = new TwoKeysHashMap<>();

	public BloodPact() {
		super("blood_pact");
		this.display_name_style.setColor("#800000");
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 34, 15 };
		this.min_cost = new int[] { 30, 12 };
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				double modified_damage = event.getDamage() * 2;
				UUID damager_uuid = damager.getUniqueId();
				UUID damagee_uuid = damagee.getUniqueId();
				Double total_damage = entity_damage_records.get(damager_uuid, damagee_uuid);
				if (total_damage == null) {
					executeTask(new Runnable() {
						@Override
						public void run() {
							Double damage = null;
							if ((damage = entity_damage_records.get(damager_uuid, damagee_uuid)) != null) {
								damager.damage(damage);
								EntityUtils.spawnParticle(damagee, Particle.ELECTRIC_SPARK, 80, 1, 1, 1);
								entity_damage_records.removeKeys(damager_uuid, damagee_uuid);
							}
						}
					}, (long) resolveEnchantmentValue() * 20);
					entity_damage_records.put(damager_uuid, damagee_uuid, 0.0);
					total_damage = 0.0;
				}
				event.setDamage(modified_damage);
				entity_damage_records.put(damager_uuid, damagee_uuid, total_damage + modified_damage);
				EntityUtils.spawnParticle(damagee, Particle.ELECTRIC_SPARK, 80, 1, 1, 1);
			}
		}
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		LivingEntity killer = entity.getKiller();
		if (killer != null) {
			setTriggerEntity(killer);
			if (resolveTrigger()) {
				entity_damage_records.removeKeys(killer.getUniqueId(), entity.getUniqueId());
			}
		}
	}

	// 根据附魔等级不同获取伤害时间
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 5;
		case 2:
			return 10;
		case 3:
			return 20;
		}
		return 0;
	}
}