package lunar.enchantments.armor;

import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Health extends ExtendedArmorEnchantment {
	public Health() {
		super("health");
		this.display_name_style.setColor("#247f00");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 26, 12 };
		this.min_cost = new int[] { 25, 10 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}
}