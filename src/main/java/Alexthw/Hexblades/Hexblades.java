package Alexthw.Hexblades;

import Alexthw.Hexblades.client.ClientEvents;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.registers.HexRegistry;
import Alexthw.Hexblades.registers.Registry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("hexblades")
public class Hexblades
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hexblades";
    public static final ItemGroup TAB = new ItemGroup(MOD_ID) {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(HexItem.PATRON_SOUL.get());
        }

    };

        public Hexblades() {
            IEventBus hexbus = FMLJavaModLoadingContext.get().getModEventBus();

            // Register the setup method for modloading
            hexbus.addListener(this::setup);

            //Register all the things
            Registry.init(hexbus);
            HexRegistry.init();

            MinecraftForge.EVENT_BUS.register(new Events());

            //Client-side only
            hexbus.addListener(this::doClientStuff);

            // Register ourselves for server and other game events we are interested in
            MinecraftForge.EVENT_BUS.register(this);

        }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(HexRegistry::post_init);
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {

        ClientEvents.initClientEvents(event);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());


    }

}
