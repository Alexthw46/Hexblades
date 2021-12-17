package alexthw.hexblades;

import alexthw.hexblades.client.ClientEvents;
import alexthw.hexblades.registers.*;
import alexthw.hexblades.util.CompatUtil;
import net.minecraft.core.Registry;
import alexthw.hexblades.world.ConfiguredStructures;
import alexthw.hexblades.world.FireTempleStructure;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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
        HexRegistry.init(hexbus);

        // For events that happen after initialization.
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.register(new Events());

        //Structures stuff
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.NORMAL, FireTempleStructure::setupStructureSpawns);

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


    private static Method GETCODEC_METHOD;


    public void addDimensionalSpacing(final WorldEvent.Load event) {

        if(event.getWorld() instanceof ServerLevel serverWorld) {
            ChunkGenerator chunkGenerator = serverWorld.getChunkSource().getGenerator();
            StructureSettings worldStructureConfig = chunkGenerator.getSettings();

            //////////// BIOME BASED STRUCTURE SPAWNING ////////////

            // Grab the map that holds what ConfigureStructures a structure has and what biomes it can spawn in.
            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            worldStructureConfig.configuredStructures.entrySet().forEach(tempStructureToMultiMap::put);


            // Create the multimap of Configured Structures to biomes we will need.
            ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> tempConfiguredStructureBiomeMultiMap = ImmutableMultimap.builder();

            // Add the registrykey of all biomes that this Configured Structure can spawn in.
            for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverWorld.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                // Skip all ocean, end, nether, and none category biomes.
                // You can do checks for other traits that the biome has.
                Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
                if (biomeCategory == Biome.BiomeCategory.NETHER) {
                    tempConfiguredStructureBiomeMultiMap.put(ConfiguredStructures.CONFIGURED_FIRE_TEMPLE, biomeEntry.getKey());
                }
            }

            // Alternative way to add our structures to a fixed set of biomes by creating a set of biome registry keys.
            // To create a custom registry key that points to your own biome, do this:
            // ResourceKey.of(Registry.BIOME_REGISTRY, new ResourceLocation("modid", "custom_biome"))
//            ImmutableSet<ResourceKey<Biome>> overworldBiomes = ImmutableSet.<ResourceKey<Biome>>builder()
//                    .add(Biomes.FOREST)
//                    .add(Biomes.MEADOW)
//                    .add(Biomes.PLAINS)
//                    .add(Biomes.SAVANNA)
//                    .add(Biomes.SNOWY_PLAINS)
//                    .add(Biomes.SWAMP)
//                    .add(Biomes.SUNFLOWER_PLAINS)
//                    .add(Biomes.TAIGA)
//                    .build();
//            overworldBiomes.forEach(biomeKey -> tempConfiguredStructureBiomeMultiMap.put(STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE, biomeKey));

            // Add the base structure to associate with this new multimap of Configured Structures to biomes to spawn in.
            tempStructureToMultiMap.put(HexStructures.FIRE_TEMPLE.get(), tempConfiguredStructureBiomeMultiMap.build());

            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();

            //Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
                //They will handle your structure spacing for your if you add to BuiltinRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
                //If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.

                try {
                    if (GETCODEC_METHOD == null)
                        GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                    ResourceLocation cgRL = net.minecraft.core.Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
                    if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
                } catch (Exception e) {
                    LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
                }


                // Prevent spawning our structure in Vanilla's superflat world as
                // people seem to want their superflat worlds free of modded structures.
                // Also that vanilla superflat is really tricky and buggy to work with in my experience.

                if (chunkGenerator instanceof FlatLevelSource &&
                        serverWorld.dimension().equals(Level.OVERWORLD)) {
                    return;
                }

                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
                tempMap.putIfAbsent(HexStructures.FIRE_TEMPLE.get(), StructureSettings.DEFAULTS.get(HexStructures.FIRE_TEMPLE.get()));
                worldStructureConfig.structureConfig = tempMap;
            }
    }


    private void registerPlacements() {
        //EntitySpawnPlacementRegistry.register(HexEntityType.TEST_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(HexEntityType.FIRE_ELEMENTAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        //EntitySpawnPlacementRegistry.register(HexEntityType.EARTH_ELEMENTAL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
    }

}
