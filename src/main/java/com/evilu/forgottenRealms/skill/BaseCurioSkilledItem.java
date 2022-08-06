package com.evilu.forgottenRealms.skill;

import com.evilu.forgottenRealms.model.Reference;
import com.evilu.forgottenRealms.model.SkillTree;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
* BaseCurioSkilledItem
*/
public class BaseCurioSkilledItem extends BaseSkilledItem implements ICurioItem {

	public BaseCurioSkilledItem(final Properties properties, final SkillTree skillTree) {
		super(properties, skillTree);
	}

	@Override
	public void onEquip(final SlotContext slotContext, final ItemStack prevStack, final ItemStack stack) {
		final Reference<SkillsData> data = getSkillsData(stack);
		data.get().getPickedSkills()
			.forEach(skill -> skill.onEnable(slotContext.entity(), data.get()));
	}

	@Override
	public void onUnequip(final SlotContext slotContext, final ItemStack newStack, final ItemStack stack) {
		final Reference<SkillsData> data = getSkillsData(stack);
		data.get().getPickedSkills()
			.forEach(skill -> skill.onDisable(slotContext.entity(), data.get()));
	}

	@Override
	public void curioTick(final SlotContext slotContext, final ItemStack stack) {
		final Reference<SkillsData> data = getSkillsData(stack);
		data.get().getPickedSkills()
			.forEach(skill -> skill.onEffectTick(slotContext.entity(), data.get()));
	}
	
}
