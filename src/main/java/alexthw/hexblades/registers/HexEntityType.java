package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static alexthw.hexblades.registers.HexItem.ITEMS;
import static alexthw.hexblades.registers.HexItem.addTabProp;

public class HexEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "hexblades");

    public static RegistryObject<EntityType<FulgorProjectileEntity>> FULGOR_PROJECTILE;
    public static RegistryObject<EntityType<MagmaProjectileEntity>> MAGMA_PROJECTILE;
    public static RegistryObject<EntityType<BaseElementalEntity>> TEST_ELEMENTAL;
    public static RegistryObject<EntityType<FireElementalEntity>> FIRE_ELEMENTAL;
    public static RegistryObject<EntityType<EarthElementalEntity>> EARTH_ELEMENTAL;

    static {
        FULGOR_PROJECTILE = registerEntity("fulgor_projectile", 0.45F, 0.2F, FulgorProjectileEntity::new, MobCategory.MISC);
        MAGMA_PROJECTILE = registerEntity("magma_projectile", 0.4F, 0.4F, MagmaProjectileEntity::new, MobCategory.MISC);
        //TEST_ELEMENTAL = addEntity("test_elemental", 0, 0, 1.0f, 2.0f, BaseElementalEntity::new, EntityClassification.MONSTER);
        FIRE_ELEMENTAL = addEntity("fire_elemental", 16167425, 800, 1.8f, 3.6f, true, FireElementalEntity::new, MobCategory.MONSTER);
        //EARTH_ELEMENTAL = addEntity("earth_elemental", 500, 800, 1.0f, 2.8f, EarthElementalEntity::new, EntityClassification.MONSTER);

    }

    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, float width, float height, EntityType.EntityFactory<T> factory, MobCategory kind) {
        EntityType<T> type = EntityType.Builder.of(factory, kind).setTrackingRange(64).setUpdateInterval(1).sized(width, height).build("hexblades:" + name);
        return ENTITIES.register(name, () -> type);
    }

    static <T extends Mob> RegistryObject<EntityType<T>> addEntity(String name, int color1, int color2, float width, float height, EntityType.EntityFactory<T> factory, MobCategory kind) {
        return addEntity(name, color1, color2, width, height, false, factory, kind);
    }

    static <T extends Mob> RegistryObject<EntityType<T>> addEntity(String name, int color1, int color2, float width, float height, boolean fire, EntityType.EntityFactory<T> factory, MobCategory kind) {
        EntityType<T> type;
        if (fire) {
            type = EntityType.Builder.of(factory, kind)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(width, height)
                    .fireImmune()
                    .build(Hexblades.MODID + ":" + name);
        } else {
            type = EntityType.Builder.of(factory, kind)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(width, height)
                    .build(Hexblades.MODID + ":" + name);
        }
        ITEMS.register("spawn_" + name, () -> new ForgeSpawnEggItem(() -> type, color1, color2, addTabProp()));
        return ENTITIES.register(name, () -> type);
    }
}
