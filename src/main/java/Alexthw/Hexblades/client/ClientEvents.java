package Alexthw.Hexblades.client;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.client.render.entity.EarthElementalER;
import Alexthw.Hexblades.client.render.entity.ElementalEntityRender;
import Alexthw.Hexblades.client.render.entity.FireElementalER;
import Alexthw.Hexblades.client.render.models.EarthElementalModel;
import Alexthw.Hexblades.client.render.models.FireElementalModel;
import Alexthw.Hexblades.client.render.models.MinionElementalModel;
import Alexthw.Hexblades.common.blocks.tile_entities.EverfullUrnBlock;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandBlock;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandRenderer;
import Alexthw.Hexblades.common.blocks.tile_entities.Urn_Renderer;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.network.WeaponAwakenPacket;
import Alexthw.Hexblades.registers.HexEntityType;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.registers.HexTileEntityType;
import Alexthw.Hexblades.util.Constants;
import Alexthw.Hexblades.util.HexUtils;
import elucent.eidolon.entity.EmptyRenderer;
import elucent.eidolon.network.Networking;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
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
import static Alexthw.Hexblades.registers.HexRegistry.HEXBLADE_KEYBINDING;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerKeybinding(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(HEXBLADE_KEYBINDING);
    }

    @SubscribeEvent
    public void onKeyPress(TickEvent.ClientTickEvent event) {

        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity playerEntity = minecraft.player;

        if (HEXBLADE_KEYBINDING.isPressed() && playerEntity != null) {
            WeaponAwakenPacket pkt = new WeaponAwakenPacket();
            Networking.sendToServer(pkt);
        }

    }

    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.SWORD_STAND_TILE_ENTITY, SwordStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.EVERFULL_URN_TILE_ENTITY, Urn_Renderer::new);
    }

    public static void initClientEvents(FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FULGOR_PROJECTILE.get(), EmptyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.TEST_ELEMENTAL.get(), (erm) -> new ElementalEntityRender(erm, new MinionElementalModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FIRE_ELEMENTAL.get(), (erm) -> new FireElementalER(erm, new FireElementalModel(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.EARTH_ELEMENTAL.get(), (erm) -> new EarthElementalER(erm, new EarthElementalModel(), 1.0F));

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
        ItemModelsProperties.registerProperty(item, rl(Constants.NBT.AW_State), (stack, world, entity) -> ((HexSwordItem) stack.getItem()).getAwakened(stack) ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        HexUtils.takeAll(blocks, b -> b.get() instanceof SwordStandBlock).forEach(ClientEvents::setCutout);
        HexUtils.takeAll(blocks, b -> b.get() instanceof EverfullUrnBlock).forEach(ClientEvents::setCutout);

    }

    public static void setCutout(RegistryObject<Block> b) {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}
