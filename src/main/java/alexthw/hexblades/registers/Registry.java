package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MODID);
    public static final DeferredRegister<MobEffect> POTIONS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Hexblades.MODID);

    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, Hexblades.MODID);

    public static void init(IEventBus hexbus) {
        hexbus.register(new HexTileEntityType());
        hexbus.register(new HexParticles());
        hexbus.addGenericListener(RecipeSerializer.class, HexSerializers::registerRecipeSerializers);

        HexBlock.BLOCKS.register(hexbus);
        ITEMS.register(hexbus);
        HexEntityType.ENTITIES.register(hexbus);
        HexTileEntityType.TILE_ENTITIES.register(hexbus);
        HexParticles.PARTICLES.register(hexbus);
        POTIONS.register(hexbus);
        POTION_TYPES.register(hexbus);
        HexStructures.STRUCTURES.register(hexbus);

    }
}
