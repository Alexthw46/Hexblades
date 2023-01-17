package alexthw.hexblades.client;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.client.render.entity.FireElementalER;
import alexthw.hexblades.client.render.tile.FirePedestalRenderer;
import alexthw.hexblades.client.render.tile.SwordStandRenderer;
import alexthw.hexblades.client.render.tile.Urn_Renderer;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.network.MiningSwitchPacket;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexTileEntityType;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.entity.EmptyRenderer;
import elucent.eidolon.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import static alexthw.hexblades.compat.ArmorCompatHandler.attachRenderers;
import static alexthw.hexblades.util.HexUtils.prefix;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    public static final KeyBinding HEXBLADE_KEYBINDING = new KeyBinding("key.hexblades.awake", GLFW.GLFW_KEY_H, "key.categories.misc");
    public static final KeyBinding HEXDRILL_KEYBINDING = new KeyBinding("key.hexblades.mining", GLFW.GLFW_KEY_J, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeybinding(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(HEXBLADE_KEYBINDING);
        ClientRegistry.registerKeyBinding(HEXDRILL_KEYBINDING);
    }

    @SubscribeEvent
    public void onKeyPress(TickEvent.ClientTickEvent event) {

        if (Minecraft.getInstance().player == null) return;

        if (HEXBLADE_KEYBINDING.consumeClick()) Networking.sendToServer(new WeaponAwakenPacket());
        else if (HEXDRILL_KEYBINDING.consumeClick()) Networking.sendToServer(new MiningSwitchPacket());

    }

    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.SWORD_STAND_TILE_ENTITY, SwordStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.FIRE_PEDESTAL_TILE_ENTITY, FirePedestalRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.EVERFULL_URN_TILE_ENTITY, Urn_Renderer::new);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void initClientEvents(FMLClientSetupEvent event) {

        GeoArmorRenderer.registerArmorRenderer(HexWArmor.class, ArmorRenderer::new);
        attachRenderers();
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FULGOR_PROJECTILE.get(), EmptyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.MAGMA_PROJECTILE.get(), EmptyRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(HexEntityType.TEST_ELEMENTAL.get(), (erm) -> new ElementalEntityRender(erm, new MinionElementalModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FIRE_ELEMENTAL.get(), FireElementalER::new);
        //RenderingRegistry.registerEntityRenderingHandler(HexEntityType.EARTH_ELEMENTAL.get(), EarthElementalER::new);

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
            registerToggleAnimation(HexItem.BLOOD_SWORD.get());

            registerToggleDrillAnimation(HexItem.EARTH_HAMMER.get());
            registerToggleDrillAnimation(HexItem.EARTH_HAMMER1.get());
        });

    }

    public static void registerToggleAnimation(Item item) {
        ItemModelsProperties.register(item, prefix(Constants.NBT.AW_State), (stack, world, entity) -> ((IHexblade) stack.getItem()).getAwakened(stack) ? 1.0F : 0.0F);
    }

    public static void registerToggleDrillAnimation(Item item) {
        ItemModelsProperties.register(item, prefix(Constants.NBT.MiningSwitch), (stack, world, entity) -> (stack.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch) ? 1.0F : 0.0F));
    }


}
