package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import Alexthw.Hexblades.common.entity.FulgorProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static Alexthw.Hexblades.registers.HexItem.addTabProp;
import static Alexthw.Hexblades.registers.Registry.ITEMS;

public class HexEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "hexblades");

    public static RegistryObject<EntityType<FulgorProjectileEntity>> FULGOR_PROJECTILE;
    public static RegistryObject<EntityType<BaseElementalEntity>> TEST_ELEMENTAL;
    public static RegistryObject<EntityType<BaseElementalEntity>> FIRE_ELEMENTAL;


    static {
        FULGOR_PROJECTILE = registerEntity("fulgor_projectile", 0.4F, 0.4F, FulgorProjectileEntity::new, EntityClassification.MISC);
        TEST_ELEMENTAL = addEntity("test_elemental", 0, 0, 1.2f, 2.5f, BaseElementalEntity::new, EntityClassification.MONSTER);
        FIRE_ELEMENTAL = addEntity("fire_elemental", 500, 800, 1.5f, 2.4f, BaseElementalEntity::new, EntityClassification.MONSTER);

    }

    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, float width, float height, EntityType.IFactory<T> factory, EntityClassification kind) {
        EntityType<T> type = EntityType.Builder.create(factory, kind).setTrackingRange(64).setUpdateInterval(1).size(width, height).build("hexblades:" + name);
        return ENTITIES.register(name, () -> type);
    }

    static <T extends Entity> RegistryObject<EntityType<T>> addEntity(String name, int color1, int color2, float width, float height, EntityType.IFactory<T> factory, EntityClassification kind) {
        EntityType<T> type = EntityType.Builder.create(factory, kind)
                .setTrackingRange(64)
                .setUpdateInterval(1)
                .size(width, height)
                .build(Hexblades.MOD_ID + ":" + name);
        ITEMS.register("spawn_" + name, () -> new SpawnEggItem(type, color1, color2, addTabProp().group(ItemGroup.MISC)));
        return ENTITIES.register(name, () -> type);
    }
}
