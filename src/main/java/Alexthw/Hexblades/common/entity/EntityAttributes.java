package Alexthw.Hexblades.common.entity;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.registers.HexEntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HexEntityType.TEST_ELEMENTAL.get(), BaseElementalEntity.createAttributes());
        event.put(HexEntityType.FIRE_ELEMENTAL.get(), FireElementalEntity.createAttributes());
        event.put(HexEntityType.EARTH_ELEMENTAL.get(), BaseElementalEntity.createAttributes());
    }
}
