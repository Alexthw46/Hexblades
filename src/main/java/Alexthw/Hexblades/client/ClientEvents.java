package Alexthw.Hexblades.client;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandBlock;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandRenderer;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexEntityType;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.registers.HexTileEntityType;
import Alexthw.Hexblades.util.Constants;
import Alexthw.Hexblades.util.HexUtils;
import elucent.eidolon.entity.EmptyRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static Alexthw.Hexblades.datagen.HexItemModelProvider.rl;
import static Alexthw.Hexblades.registers.HexBlock.BLOCKS;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {


    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.SWORD_STAND_TILE_ENTITY, SwordStandRenderer::new);
    }

    public static void initClientEvents(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FULGOR_PROJECTILE.get(), EmptyRenderer::new);

        DeferredWorkQueue.runLater(() -> {

            //Awakening Toggles
            registerToggleAnimation(HexItem.FROST_RAZOR.get());
            registerToggleAnimation(HexItem.FROST_RAZOR1.get());
            registerToggleAnimation(HexItem.FIRE_BRAND.get());
            registerToggleAnimation(HexItem.FIRE_BRAND1.get());
            registerToggleAnimation(HexItem.WATER_SABER.get());
            registerToggleAnimation(HexItem.WATER_SABER1.get());
            registerToggleAnimation(HexItem.EARTH_HAMMER.get());
            registerToggleAnimation(HexItem.EARTH_HAMMER1.get());
            registerToggleAnimation(HexItem.LIGHTNING_DAGGER_L.get());
            registerToggleAnimation(HexItem.LIGHTNING_DAGGER_R.get());
            registerToggleAnimation(HexItem.LIGHTNING_SSWORD_L.get());
            registerToggleAnimation(HexItem.LIGHTNING_SSWORD_R.get());
        });

    }

    public static void registerToggleAnimation(Item item) {
        ItemModelsProperties.registerProperty(item, rl(Constants.NBT.AW_State), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack stack, ClientWorld world, LivingEntity entity) {
                return ((HexSwordItem) stack.getItem()).getAwakened(stack) ? 1.0F : 0.0F;
            }
        });
    }

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        HexUtils.takeAll(blocks, b -> b.get() instanceof SwordStandBlock).forEach(ClientEvents::setCutout);
    }

    public static void setCutout(RegistryObject<Block> b) {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}
