package com.evilu.forgottenRealms.skill;

import com.evilu.forgottenRealms.gui.skill.SkillWindow;
import com.evilu.forgottenRealms.model.SkillTree;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * BaseSkilledItem
 */
public abstract class BaseSkilledItem extends Item implements SkilledItem {

	protected final SkillTree skillTree;

	public BaseSkilledItem(final Item.Properties properties, final SkillTree skillTree) {
		super(properties);
		this.skillTree = skillTree;
	}

	@Override
	public SkillTree getSkillTree() {
		return skillTree;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand) {
		final InteractionResultHolder<ItemStack> stackHolder = super.use(level, player, hand);

		if (!(level instanceof ServerLevel) && hand == InteractionHand.MAIN_HAND) {
			final ItemStack stack = stackHolder.getObject();
			new SkillWindow(skillTree, getSkillsData(stack), player, hand).open();

		}

		return stackHolder;
	}

}
