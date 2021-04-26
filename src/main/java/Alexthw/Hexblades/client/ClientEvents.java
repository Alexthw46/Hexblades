package Alexthw.Hexblades.client;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.util.Constants;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static Alexthw.Hexblades.datagen.HexItemModelProvider.rl;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {


    public static void initClientEvents(FMLClientSetupEvent event) {

        DeferredWorkQueue.runLater(() -> {

            registerToggleAnimation(HexItem.FROST_RAZOR.get());
            registerToggleAnimation(HexItem.FROST_RAZOR1.get());
            registerToggleAnimation(HexItem.FIRE_BRAND.get());
            registerToggleAnimation(HexItem.FIRE_BRAND1.get());
            registerToggleAnimation(HexItem.WATER_SABER.get());
            registerToggleAnimation(HexItem.WATER_SABER1.get());
            registerToggleAnimation(HexItem.EARTH_HAMMER.get());
            //registerToggleAnimation(HexItem.EARTH_HAMMER1.get());
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
}
