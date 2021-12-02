package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import elucent.eidolon.Eidolon;
import elucent.eidolon.block.BlockBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class HexTileEntityType {

    public HexTileEntityType() {
    }

    public static BlockEntityType<SwordStandTileEntity> SWORD_STAND_TILE_ENTITY;
    public static BlockEntityType<EverfullUrnTileEntity> EVERFULL_URN_TILE_ENTITY;
    public static BlockEntityType<FirePedestalTileEntity> FIRE_PEDESTAL_TILE_ENTITY;

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Hexblades.MODID);

    @SubscribeEvent
    public void registerTiles(RegistryEvent.Register<BlockEntityType<?>> evt) {
        SWORD_STAND_TILE_ENTITY = addTileEntity(evt.getRegistry(), "sword_stand_tile", SwordStandTileEntity::new, (BlockBase) HexBlock.SWORD_STAND.get());
        EVERFULL_URN_TILE_ENTITY = addTileEntity(evt.getRegistry(), "everfull_urn_tile", EverfullUrnTileEntity::new, (BlockBase) HexBlock.EVERFULL_URN.get());
        FIRE_PEDESTAL_TILE_ENTITY = addTileEntity(evt.getRegistry(), "fire_pedestal_tile", FirePedestalTileEntity::new, (BlockBase) HexBlock.FIRE_PEDESTAL.get());
    }

    static <T extends BlockEntity> BlockEntityType<T> addTileEntity(IForgeRegistry<BlockEntityType<?>> registry, String name, BlockEntityType.BlockEntitySupplier<T> factory, BlockBase block) {
        BlockEntityType<T> type = BlockEntityType.Builder.of(factory, block).build(null);
        type.setRegistryName(Hexblades.MODID, name);
        registry.register(type);
        block.setTile(type);
        return type;
    }


}
