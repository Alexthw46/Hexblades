package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.DeityLocks;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.deity.HexFacts;
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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
        if (!world.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)) {
                    world.getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                        Deity deity = HexDeities.HEX_DEITY;
                        if (rep.unlock(player, deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                            deity.onReputationUnlock(player, rep, DeityLocks.EVOLVED_WEAPON);
                        }
                    });
                }
            } else {
                rep = player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
                NewChatGui chat = Minecraft.getInstance().gui.getChat();
                chat.addMessage(new TranslationTextComponent("Current reputation: " + rep));
                chat.addMessage(new TranslationTextComponent("Knows Awakening: " + KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL)));
                chat.addMessage(new TranslationTextComponent("Knows Evolving: " + KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)));
                chat.addMessage(new TranslationTextComponent("Knows Infusion: " + KnowledgeUtil.knowsFact(player, HexFacts.STAR_INFUSION)));

            }
        }
        return super.use(world, player, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}
