package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.util.Constants;
import Alexthw.Hexblades.core.util.NBTHelper;
import Alexthw.Hexblades.patrons.HexDeities;
import com.google.common.collect.Lists;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class PatronSoul extends Item {

    public PatronSoul(Properties properties) {
        super(properties);
    }
    static double rep;
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {

        if (!worldIn.isRemote) {
            rep = player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
            NewChatGui chat = Minecraft.getInstance().ingameGUI.getChatGUI();
            chat.printChatMessage(new TranslationTextComponent("Current reputation: " + rep));
        }

        return super.onItemRightClick(worldIn, player, handIn);
    }

    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
