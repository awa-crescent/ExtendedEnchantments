package lunar.enchantments.weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import lib.crescent.item.ItemUtils;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Capture extends ExtendedMainhandEnchantment {
	public Capture() {
		super("capture");
		this.setWeight(3);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 25, 15 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:beheading"));
		this.is_treasure = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		LivingEntity killer = entity.getKiller();
		if (killer != null) {
			setTriggerEntity(killer);
			if (resolveTrigger()) {
				ItemStack spawn_egg = ItemUtils.getSpawnEgg(entity.getType());
				if (spawn_egg != null)
					entity.getWorld().dropItemNaturally(entity.getLocation(), spawn_egg);
			}
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.05;
		case 2:
			return 0.10;
		case 3:
			return 0.20;
		}
		return 0;
	}
}