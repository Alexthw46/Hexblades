package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MODID);
    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Hexblades.MODID);

    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Hexblades.MODID);

    public static void init(IEventBus hexbus) {
        hexbus.register(new HexTileEntityType());
        hexbus.register(new HexParticles());
        hexbus.addGenericListener(IRecipeSerializer.class, HexSerializers::registerRecipeSerializers);

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
