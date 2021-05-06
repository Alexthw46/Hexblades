package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import elucent.eidolon.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class HexTileEntityType {
    public static TileEntityType<SwordStandTileEntity> SWORD_STAND_TILE_ENTITY;
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Hexblades.MOD_ID);

    @SubscribeEvent
    public void registerTiles(RegistryEvent.Register<TileEntityType<?>> evt) {
        SWORD_STAND_TILE_ENTITY = addTileEntity(evt.getRegistry(), "sword_stand_tile", SwordStandTileEntity::new, HexBlock.SWORD_STAND.get());
    }

    static <T extends TileEntity> TileEntityType<T> addTileEntity(IForgeRegistry<TileEntityType<?>> registry, String name, Supplier<T> factory, Block block) {
        TileEntityType<T> type = TileEntityType.Builder.<T>create(factory, block).build(null);
        type.setRegistryName(Hexblades.MOD_ID, name);
        registry.register(type);
        ((BlockBase) block).setTile(type);
        return type;
    }

}
