package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MOD_ID);

    public static void init(IEventBus hexbus) {

        HexBlock.BLOCKS.register(hexbus);
        ITEMS.register(hexbus);
        HexEntityType.ENTITIES.register(hexbus);
        HexTileEntityType.TILE_ENTITIES.register(hexbus);
        HexRegistry.PARTICLES.register(hexbus);
    }
}
