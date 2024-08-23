package lunar.enchantments.weapon;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.BossBarEffect;
import lunar.ExtendedEnchantments;
import lunar.template.ExtendedMainhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Sanctuary extends ExtendedMainhandEnchantment {
	private BossBarEffect vision_effect = BossBarEffect.createBossBarVisionEffect(ExtendedEnchantments.plugin_name, "text.bossbar.sanctuary", display_name_style, true, false).setVisible(true);

	private HashSet<UUID> holder_set = new HashSet<>();

	public Sanctuary() {
		super("sanctuary");
		this.display_name_style.setColor("#FFFFE0");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 85, 25 };
		this.min_cost = new int[] { 80, 20 };
		this.is_discoverable = false;
		this.is_treasure = true;
		this.is_tradeable = false;
	}
/*
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager) {
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				event.setDamage(event.getDamage() * 1.2);
				EntityUtils.spawnParticle(event.getEntity(), Particle.WAX_ON, 20, 0.5, 1, 0.5);
			}
		}
		if ((event.getEntity() )) {
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				event.setDamage(event.getDamage() * 0.8);
				EntityUtils.spawnParticle(damagee, Particle.WAX_OFF, 20, 0.5, 1, 0.5);
			}
		}
	}

	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		boolean is_holder = holder_set.contains(uuid);
		setTriggerEntity(player);
		setTriggerItemAtIndex(event.getNewSlot());
		if (resolveTrigger()) {
			World world = player.getWorld();
			vision_effect.attachDisplayEntity(player, 64, 64, 64, true);
			if (cooldown_set.contains(uuid))
				return;
			if (!is_holder)
				holder_set.add(uuid);
			List<Entity> nearby_entities = player.getNearbyEntities(16, 16, 16);
			for (Entity entity : nearby_entities)
				if (entity != player && entity instanceof LivingEntity target) {
					int ench_level = getLevel();
					switch (ench_level) {
					case 3:
						target.setHealth(target.getHealth() * 0.5);
					case 2:
						world.strikeLightning(target.getLocation());
					case 1:
						target.setFireTicks(100);
						EntityUtils.playSoundToAllPlayers(world, Sound.ENTITY_ENDER_DRAGON_GROWL, 1);
						cooldown_set.add(player.getUniqueId());
						executeTask(new Runnable() {
							@Override
							public void run() {
								cooldown_set.remove(player.getUniqueId());
							}
						}, (long) (resolveEnchantmentValue() * 20));
						break;
					}
					EntityUtils.spawnParticle(target, Particle.EXPLOSION_EMITTER, 20, 1, 1, 1);
				}
		} else {
			if (is_holder)
				vision_effect.detachDisplayEntity();
		}
	}

	// 根据附魔等级不同获取冷却时间
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 20;
		case 2:
			return 20;
		case 3:
			return 30;
		}
		return 0;
	}*/
}