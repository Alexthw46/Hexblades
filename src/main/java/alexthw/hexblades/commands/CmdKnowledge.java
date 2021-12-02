package alexthw.hexblades.commands;

import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import elucent.eidolon.Eidolon;
import elucent.eidolon.spell.KnowledgeUtil;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

public class CmdKnowledge {

    private static final CmdKnowledge CMD = new CmdKnowledge();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("knowledge")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("give")
                                .then(Commands.argument("knowledge", new KnowledgeArgumentType())
                                        .executes(ctx -> CMD.giveKnowledge(EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("knowledge", String.class)))
                                )
                        )
                );
    }

    private int giveKnowledge(ServerPlayer player, String knowledgeName) {

        Sign sign = Signs.find(new ResourceLocation(Eidolon.MODID, knowledgeName));

        if (sign == null) {
            return 0;
        }

        KnowledgeUtil.grantSign(player, sign);

        return Command.SINGLE_SUCCESS;
    }

}
