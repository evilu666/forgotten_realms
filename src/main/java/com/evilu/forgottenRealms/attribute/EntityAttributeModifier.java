package com.evilu.forgottenRealms.attribute;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * EntityAttributeModifier
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EntityAttributeModifier {

    @AllArgsConstructor
    public static enum EntityAttributeModifierOperation {
        MUL((attribute, param) -> attribute * param),
        DIV((attribute, param) -> attribute / param),
        ADD((attribute, param) -> attribute + param),
        SUB((attribute, param) -> attribute - param),
        ID((attribute, param) -> attribute)
        ;

        private final BiFunction<Double, Double, Double> op;

        private double apply(final double attribute, final double param) {
            return op.apply(attribute, param);
        }
    }

    public static final Codec<EntityAttributeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(EntityAttributeModifier::getId),
        Codec.DOUBLE.fieldOf("parameter").forGetter(EntityAttributeModifier::getParameter),
        Codec.STRING.xmap(EntityAttributeModifierOperation::valueOf, EntityAttributeModifierOperation::name).fieldOf("operation").forGetter(EntityAttributeModifier::getOperation)
    ).apply(instance, EntityAttributeModifier::new));

    public static EntityAttributeModifier combine(final Collection<EntityAttributeModifier> modfiers) {
        if (modfiers.isEmpty()) return identity();

        double param = 0d;
        EntityAttributeModifierOperation op = null;
        for (final EntityAttributeModifier mod : modfiers) {
            if (op == null) op = mod.operation;
            if (mod.operation != op) throw new IllegalArgumentException(String.format("Expected operation '%s' bot got '%s' instead", op.name(), mod.operation.name()));
            param += mod.parameter;
        }

        return new EntityAttributeModifier(UUID.randomUUID().toString(), param, op);
    }

    public static EntityAttributeModifier identity() {
        return new EntityAttributeModifier(UUID.randomUUID().toString(), 0d, EntityAttributeModifierOperation.ID);
    }

    public static EntityAttributeModifier multiply(final double parameter) {
        return new EntityAttributeModifier(UUID.randomUUID().toString(), parameter, EntityAttributeModifierOperation.MUL);
    }

    private final String id;
    @Setter
    private double parameter;
    private final EntityAttributeModifierOperation operation;

    public double apply(final double attribute) {
        return operation.apply(attribute, parameter);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntityAttributeModifier) {
            return id.equals(((EntityAttributeModifier) obj).id);
        }

        return false;
    }
}
