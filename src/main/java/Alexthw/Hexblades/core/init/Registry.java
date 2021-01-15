package Alexthw.Hexblades.core.init;

import net.minecraftforge.eventbus.api.IEventBus;

public class Registry {
    public static void init(IEventBus hexbus) {
        ItemInit.ITEMS.register(hexbus);
        BlockInit.BLOCKS.register(hexbus);
    }
}
