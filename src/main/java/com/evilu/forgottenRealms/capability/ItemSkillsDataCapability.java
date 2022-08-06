package com.evilu.forgottenRealms.capability;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.registry.CapabilityRegistry;
import com.evilu.forgottenRealms.skill.BaseSkilledItem;
import com.evilu.forgottenRealms.skill.SkillsData;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * ItemSkillsDataCapability
 */
@EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Bus.FORGE)
public class ItemSkillsDataCapability extends AbstractDataCapability<SkillsData, SkillsDataCapability> implements SkillsDataCapability {

    public ItemSkillsDataCapability() {
        super(SkillsData.CODEC);
    }

    @Override
    protected SkillsData createEmpty() {
        return SkillsData.createEmpty();
    }

    @SubscribeEvent
    public static void attachLivingEntityCaps(final AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof BaseSkilledItem) {
            event.addCapability(CapabilityRegistry.SKILLS_DATA_LOCATION, createCapabilityProvider(CapabilityRegistry.SKILLS_DATA, new ItemSkillsDataCapability()));
        }
    }
}
