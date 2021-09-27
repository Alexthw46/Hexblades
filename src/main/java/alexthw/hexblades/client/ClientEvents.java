package alexthw.hexblades.client;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.FireElementalER;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandRenderer;
import alexthw.hexblades.common.blocks.tile_entities.Urn_Renderer;
import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexTileEntityType;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.entity.EmptyRenderer;
import elucent.eidolon.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
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

import static alexthw.hexblades.datagen.HexItemModelProvider.rl;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    public static final KeyBinding HEXBLADE_KEYBINDING = new KeyBinding("key.hexblades.awake", GLFW.GLFW_KEY_H, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeybinding(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(HEXBLADE_KEYBINDING);
    }

    @SubscribeEvent
    public void onKeyPress(TickEvent.ClientTickEvent event) {

        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity playerEntity = minecraft.player;

        if (HEXBLADE_KEYBINDING.consumeClick() && playerEntity != null) {
            WeaponAwakenPacket pkt = new WeaponAwakenPacket();
            Networking.sendToServer(pkt);
        }

    }

    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.SWORD_STAND_TILE_ENTITY, SwordStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HexTileEntityType.EVERFULL_URN_TILE_ENTITY, Urn_Renderer::new);
    }

    @SubscribeEvent
    public static void initClientEvents(FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(HexEntityType.FULGOR_PROJECTILE.get(), EmptyRenderer::new);
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

        });

    }

    public static void registerToggleAnimation(Item item) {
        ItemModelsProperties.register(item, rl(Constants.NBT.AW_State), (stack, world, entity) -> ((HexSwordItem) stack.getItem()).getAwakened(stack) ? 1.0F : 0.0F);
    }

}
