package lunar.enchantments.armor;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityAttributeModifier;
import lib.crescent.entity.EntityRelatedIntValue;
import lib.crescent.entity.EntityUtils;
import lib.crescent.entity.ValueType;
import lib.crescent.utils.format.FormattingCode;
import lunar.ExtendedEnchantments;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Charged extends ExtendedArmorEnchantment {
	private EntityAttributeModifier attrib_modifier = new EntityAttributeModifier(ExtendedEnchantments.plugin_name);
	private EntityRelatedIntValue charge_counter = new EntityRelatedIntValue(ExtendedEnchantments.plugin_name);

	public Charged() {
		super("charged");
		this.level_type = ArmorLevelType.MAX;
		this.display_name_style.setColor(FormattingCode.YELLOW);
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:conductive"));
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	private void chargeEntity(LivingEntity entity, int stage) {
		switch (stage) {
		case 1:
			entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, -1, false, false, false));
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, -1, false, false, false));
			break;
		case 2:
			entity.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, PotionEffect.INFINITE_DURATION, 0, false, false, false));
			break;
		case 3:
		default:
			attrib_modifier.addBaseValue(entity, Attribute.GENERIC_MAX_HEALTH, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.5, true);
			break;
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LightningStrike) {// 伤害来源于雷击
			if (event.getEntity() instanceof LivingEntity damagee) {
				setTriggerEntity(damagee);
				if (resolveTrigger()) {
					charge_counter.increaseValue(damagee, true);
					int counter = charge_counter.getValue(damagee, ValueType.CLEAR_ON_DEATH);
					if (counter > 3)
						return;
					chargeEntity(damagee, Math.min(getLevel(), counter));
					EntityUtils.spawnParticle(damagee, Particle.ELECTRIC_SPARK, 80, 1, 1, 1);
				}
			}
		}
	}
}