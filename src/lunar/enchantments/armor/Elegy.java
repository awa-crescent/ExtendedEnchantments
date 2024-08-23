package lunar.enchantments.armor;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lib.crescent.utils.trigger.Trigger;
import lib.crescent.utils.trigger.TriggerCondition;
import lunar.template.ExtendedArmorEnchantment;

//哀歌
public class Elegy extends ExtendedArmorEnchantment {
	private boolean has_generated_lightning = false;

	public Elegy() {
		super("elegy");
		this.setArmorType(ArmorType.FOOT);
		this.level_type = ArmorLevelType.MAX;
		this.display_name_style.setColor(FormattingCode.AQUA);
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
		setExtraTriggerCondition(new TriggerCondition<World>() {
			public boolean checkTriggerCondition(World world) {
				return !world.isClearWeather();
			}
		});
	}

	@EventHandler
	public void onWeatherChangeEvent(WeatherChangeEvent event) {
		World world = event.getWorld();
		Collection<? extends Player> players = EntityUtils.getAllPlayers(world);
		for (Player player : players) {
			setTriggerEntity(player);
			if (resolveTrigger()) {

				if (world.isClearWeather()) {// 雨停了立即清除挖掘疲劳
					player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
				} else// 开始下雨了则定时给玩家设置挖掘疲劳
					executeTask(new Runnable() {
						@Override
						public void run() {
							if (!world.isClearWeather())
								player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, PotionEffect.INFINITE_DURATION, -1, false, false, false));
						}
					}, (long) (resolveEnchantmentValue(1) * 20));
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damager) {
			setTriggerEntity(damager);
			if (resolveTrigger(event.getDamager().getWorld())) {
				event.setDamage(event.getDamage() * (1 + resolveEnchantmentValue(0)));
			}
		}
	}

	@EventHandler
	public void onLightningStrikeEvent(LightningStrikeEvent event) {
		Collection<Entity> entities = event.getLightning().getNearbyEntities(8, 8, 8);
		for (Entity entity : entities) {
			if (!(entity instanceof LivingEntity))
				continue;
			setTriggerEntity((LivingEntity) entity);
			if (resolveTrigger(entity.getWorld())) {// 当前世界下雨时，才有概率被雷击
				if (Trigger.resolveTrigger(resolveEnchantmentValue(2))) {
					if (has_generated_lightning) {
						has_generated_lightning = false;
						return;
					} else {
						has_generated_lightning = true;
						event.getWorld().strikeLightning(entity.getLocation());
						event.setCancelled(true);
					}
				}
			}
		}
	}

	// 数组成员：提升伤害百分比、淋雨时长（秒）、雷击概率
	@Override
	public double[] getEnchantmentValues(int level) {
		double[] arr = null;
		switch (level) {
		case 1:
			arr = new double[] { 0.1, 30, 0.6 };
			break;
		case 2:
			arr = new double[] { 0.2, 90, 0.45 };
			break;
		case 3:
			arr = new double[] { 0.3, 150, 0.3 };
			break;
		}
		return arr;
	}

}