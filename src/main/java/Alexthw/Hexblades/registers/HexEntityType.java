package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.common.entity.FulgorProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HexEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "hexblades");

    public static RegistryObject<EntityType<FulgorProjectileEntity>> FULGOR_PROJECTILE;

    static {
        FULGOR_PROJECTILE = registerEntity("fulgor_projectile", 0.4F, 0.4F, FulgorProjectileEntity::new, EntityClassification.MISC);
    }

    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, float width, float height, EntityType.IFactory<T> factory, EntityClassification kind) {
        EntityType<T> type = EntityType.Builder.create(factory, kind).setTrackingRange(64).setUpdateInterval(1).size(width, height).build("hexblades:" + name);
        return ENTITIES.register(name, () -> type);
    }

}
