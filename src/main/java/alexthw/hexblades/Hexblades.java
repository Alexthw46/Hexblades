package alexthw.hexblades;

import alexthw.hexblades.client.ClientEvents;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.registers.Registry;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("hexblades")
public class Hexblades {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hexblades";
    public static final ItemGroup TAB = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HexItem.PATRON_SOUL2.get());
        }
    };

    public Hexblades() {
        IEventBus hexbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        // Register the setup
        GeckoLib.initialize();
        hexbus.addListener(this::setup);

        //Register all the things
        Registry.init(hexbus);
        HexRegistry.init();

        MinecraftForge.EVENT_BUS.register(new Events());

        //Client-side only
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            return new Object();
        });


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(HexRegistry::post_init);
        this.registerPlacements();
    }


    private void registerPlacements() {
        //EntitySpawnPlacementRegistry.register(HexEntityType.TEST_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(HexEntityType.FIRE_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        //EntitySpawnPlacementRegistry.register(HexEntityType.EARTH_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
    }

}
