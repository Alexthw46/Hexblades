package Alexthw.Hexblades.core.init;

import net.minecraftforge.eventbus.api.IEventBus;

public class Registry {
    public static void init(IEventBus hexbus) {
        HexItem.ITEMS.register(hexbus);
        HexBlock.BLOCKS.register(hexbus);
    }
}
