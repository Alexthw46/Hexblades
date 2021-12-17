package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class Urn_Renderer<T extends BlockEntity> implements BlockEntityRenderer<EverfullUrnTileEntity> {

    public Urn_Renderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(@NotNull EverfullUrnTileEntity p_112307_, float p_112308_, @NotNull PoseStack p_112309_, @NotNull MultiBufferSource p_112310_, int p_112311_, int p_112312_) {

    }
}
