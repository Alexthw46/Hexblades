package alexthw.hexblades;

import alexthw.hexblades.client.ClientEvents;
import alexthw.hexblades.registers.*;
import alexthw.hexblades.util.CompatUtil;
import alexthw.hexblades.util.WorldGenUtil;
import alexthw.hexblades.world.ConfiguredStructures;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod(Hexblades.MODID)
public class Hexblades {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "hexblades";
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        @Nonnull
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
        CompatUtil.check();
        Registry.init(hexbus);
        HexRegistry.init();

        // For events that happen after initialization.
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.register(new Events());

        //Structures stuff
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

        //Client-side only
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            return new Object();
        });


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HexStructures.setupStructures();
            ConfiguredStructures.registerConfiguredStructures();
            HexRegistry.post_init();
        });
        this.registerPlacements();
    }

    /**
     * Add our structure to all biomes including other modded biomes.
     * You can skip or add only to certain biomes based on stuff like biome category,
     * temperature, scale, precipitation, mod id, etc. All kinds of options!
     * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
     * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
     * registrykey. Then that can be fed into the dictionary to get the biome's types.
     **/


    public void biomeModification(final BiomeLoadingEvent event) {

        if (WorldGenUtil.haveCategories(event, Biome.BiomeCategory.NETHER)) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FIRE_TEMPLE);
        }
    }

    private static Method GETCODEC_METHOD;


    public void addDimensionalSpacing(final WorldEvent.Load event) {

        if (event.getWorld() instanceof ServerLevel serverWorld) {
            //Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             //They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             //If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = net.minecraft.core.Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }


             // Prevent spawning our structure in Vanilla's superflat world as
             // people seem to want their superflat worlds free of modded structures.
             // Also that vanilla superflat is really tricky and buggy to work with in my experience.

            if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource &&
                    serverWorld.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(HexStructures.FIRE_TEMPLE.get(), StructureSettings.DEFAULTS.get(HexStructures.FIRE_TEMPLE.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }


    private void registerPlacements() {
        //EntitySpawnPlacementRegistry.register(HexEntityType.TEST_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(HexEntityType.FIRE_ELEMENTAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        //EntitySpawnPlacementRegistry.register(HexEntityType.EARTH_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
    }

}
