package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.deity.DeityLocks;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.deity.HexFacts;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                if (!KnowledgeUtil.knowsFact(player, HexFacts.STAR_INFUSION)) {
                    world.getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                        Deity deity = HexDeities.HEX_DEITY;
                        if (rep.unlock(player, deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                            deity.onReputationUnlock(player, rep, DeityLocks.EVOLVED_WEAPON);
                        }
                    });
                }
            } else {
                rep = player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
                NewChatGui chat = Minecraft.getInstance().ingameGUI.getChatGUI();
                chat.printChatMessage(new TranslationTextComponent("Current reputation: " + rep));
                chat.printChatMessage(new TranslationTextComponent("Knows Awakening: " + KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL)));
                chat.printChatMessage(new TranslationTextComponent("Knows Evolving: " + KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)));
                chat.printChatMessage(new TranslationTextComponent("Knows Infusion: " + KnowledgeUtil.knowsFact(player, HexFacts.STAR_INFUSION)));

            }
        }
        return super.onItemRightClick(world, player, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
