package alexthw.hexblades.commands;

import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import elucent.eidolon.Eidolon;
import elucent.eidolon.spell.KnowledgeUtil;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class CmdKnowledge {

    private static final CmdKnowledge CMD = new CmdKnowledge();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
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

    private int giveKnowledge(ServerPlayerEntity player, String knowledgeName) {

        Sign sign = Signs.find(new ResourceLocation(Eidolon.MODID, knowledgeName));

        if (sign == null) {
            return 0;
        }

        KnowledgeUtil.grantSign(player, sign);

        return Command.SINGLE_SUCCESS;
    }

}
