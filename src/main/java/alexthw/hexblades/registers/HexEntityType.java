package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.BaseElementalEntity;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import alexthw.hexblades.common.entity.FireElementalEntity;
import alexthw.hexblades.common.entity.FulgorProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static alexthw.hexblades.registers.HexItem.ITEMS;
import static alexthw.hexblades.registers.HexItem.addTabProp;

public class HexEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "hexblades");

    public static RegistryObject<EntityType<FulgorProjectileEntity>> FULGOR_PROJECTILE;
    public static RegistryObject<EntityType<BaseElementalEntity>> TEST_ELEMENTAL;
    public static RegistryObject<EntityType<FireElementalEntity>> FIRE_ELEMENTAL;
    public static RegistryObject<EntityType<EarthElementalEntity>> EARTH_ELEMENTAL;

    static {
        FULGOR_PROJECTILE = registerEntity("fulgor_projectile", 0.4F, 0.4F, FulgorProjectileEntity::new, EntityClassification.MISC);
        //TEST_ELEMENTAL = addEntity("test_elemental", 0, 0, 1.0f, 2.0f, BaseElementalEntity::new, EntityClassification.MONSTER);
        FIRE_ELEMENTAL = addEntity("fire_elemental", 16167425, 800, 1.8f, 3.6f, true, FireElementalEntity::new, EntityClassification.MONSTER);
        //EARTH_ELEMENTAL = addEntity("earth_elemental", 500, 800, 1.0f, 2.8f, EarthElementalEntity::new, EntityClassification.MONSTER);

    }

    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, float width, float height, EntityType.IFactory<T> factory, EntityClassification kind) {
        EntityType<T> type = EntityType.Builder.of(factory, kind).setTrackingRange(64).setUpdateInterval(1).sized(width, height).build("hexblades:" + name);
        return ENTITIES.register(name, () -> type);
    }

    static <T extends Entity> RegistryObject<EntityType<T>> addEntity(String name, int color1, int color2, float width, float height, EntityType.IFactory<T> factory, EntityClassification kind) {
        return addEntity(name, color1, color2, width, height, false, factory, kind);
    }

    static <T extends Entity> RegistryObject<EntityType<T>> addEntity(String name, int color1, int color2, float width, float height, boolean fire, EntityType.IFactory<T> factory, EntityClassification kind) {
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
        ITEMS.register("spawn_" + name, () -> new SpawnEggItem(type, color1, color2, addTabProp()));
        return ENTITIES.register(name, () -> type);
    }
}
