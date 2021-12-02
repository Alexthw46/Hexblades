package alexthw.hexblades.commands;

import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.deity.HexFacts;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;

public class CmdFacts {

    private static final CmdFacts CMD = new CmdFacts();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("facts")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("give")
                                .then(Commands.argument("fact", new FactArgumentType())
                                        .executes(ctx -> CMD.giveFact(EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("fact", String.class)))
                                )
                        )
                        .then(Commands.literal("has")
                                .then(Commands.argument("fact", new FactArgumentType())
                                        .executes(ctx -> CMD.hasFact(ctx, EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("fact", String.class)))
                                )
                        )
                );
    }

    public int giveFact(Player player, String factName) {
        ResourceLocation fact;
        switch (factName) {
            case ("awakening_ritual"):
                fact = HexFacts.AWAKENING_RITUAL;
                break;
            case ("evolution_ritual"):
                fact = HexFacts.EVOLVE_RITUAL;
                break;
            case ("elemental_summoning"):
                fact = HexFacts.ELEMENTAL_SUMMON;
                break;
            case ("villager_sacrifice"):
                fact = HexFacts.VILLAGER_SACRIFICE;
                break;
            default:
                return 0;
        }

        if (!KnowledgeUtil.knowsFact(player, fact)) {
            KnowledgeUtil.grantFact(player, fact);
        }

        return Command.SINGLE_SUCCESS;
    }

    public int hasFact(CommandContext<CommandSourceStack> ctx, Player player, String factName) {
        ResourceLocation fact;
        switch (factName) {
            case ("awakening_ritual"):
                fact = HexFacts.AWAKENING_RITUAL;
                break;
            case ("evolution_ritual"):
                fact = HexFacts.EVOLVE_RITUAL;
                break;
            case ("star_infusion"):
                fact = HexFacts.ELEMENTAL_SUMMON;
                break;
            case ("villager_sacrifice"):
                fact = HexFacts.VILLAGER_SACRIFICE;
                break;
            default:
                return 0;
        }
        if (KnowledgeUtil.knowsFact(player, fact)) {
            ctx.getSource().sendSuccess(new TextComponent("true"), false);
        } else {
            ctx.getSource().sendFailure(new TextComponent("false"));
        }
        return Command.SINGLE_SUCCESS;
    }

}
