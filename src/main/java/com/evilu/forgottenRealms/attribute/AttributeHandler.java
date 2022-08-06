package com.evilu.forgottenRealms.attribute;

import com.evilu.forgottenRealms.ForgottenRealmsMod;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * AttributeHandler
 */
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Bus.FORGE)
public class AttributeHandler {

    @SubscribeEvent
    public static void handleLivingJumpEvent(final LivingJumpEvent event) {
        final LivingEntity entity = event.getEntityLiving();
        final EntityAttributeData data = EntityAttributeData.read(entity);
        final double strength = data.calculateValue(EntityAttribute.STRENGTH);
        final double jumpBoost = Math.pow((Math.log(strength + 258) * 90) - 500, 1d/4d) - 0.8d;
        if (jumpBoost > 0 && Double.isFinite(jumpBoost)) {
            final Vec3 boostDir = new Vec3(0, jumpBoost, 0);
            final Vec3 lookDir = entity.getLookAngle().scale(boostDir.length());

            final Vec3 boost = new Vec3((boostDir.x + lookDir.x) / 2, (boostDir.y + lookDir.y) / 2, (boostDir.z + lookDir.z) / 2);

            entity.setDeltaMovement(boost.add(entity.getDeltaMovement()));
        }
    }
}
