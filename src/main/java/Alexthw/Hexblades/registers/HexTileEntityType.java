package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import elucent.eidolon.block.BlockBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class HexTileEntityType {

    public HexTileEntityType(){}

    public static TileEntityType<SwordStandTileEntity> SWORD_STAND_TILE_ENTITY;
    public static TileEntityType<EverfullUrnTileEntity> EVERFULL_URN_TILE_ENTITY;
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Hexblades.MOD_ID);

    @SubscribeEvent
    public void registerTiles(RegistryEvent.Register<TileEntityType<?>> evt) {
        SWORD_STAND_TILE_ENTITY = addTileEntity(evt.getRegistry(), "sword_stand_tile", SwordStandTileEntity::new, (BlockBase) HexBlock.SWORD_STAND.get());
        EVERFULL_URN_TILE_ENTITY = addTileEntity(evt.getRegistry(), "everfull_urn_tile", EverfullUrnTileEntity::new, (BlockBase) HexBlock.EVERFULL_URN.get());
    }

    static <T extends TileEntity> TileEntityType<T> addTileEntity(IForgeRegistry<TileEntityType<?>> registry, String name, Supplier<T> factory, BlockBase block) {
        TileEntityType<T> type = TileEntityType.Builder.create(factory, block).build(null);
        type.setRegistryName(Hexblades.MOD_ID, name);
        registry.register(type);
        block.setTile(type);
        return type;
    }

}
