package lunar.template;

import lib.crescent.enchantment.template.ArmorEnchantment;
import lunar.ExtendedEnchantments;

public class ExtendedArmorEnchantment extends ArmorEnchantment {

	public ExtendedArmorEnchantment(String enchantment_id) {
		super(ExtendedEnchantments.plugin_name, "lunar", enchantment_id, "enchantments.armor." + enchantment_id);
	}

}
