package alexthw.hexblades.commands;

import alexthw.hexblades.commands.args.DeityArgumentType;
import alexthw.hexblades.deity.HexDeities;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.deity.Deity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CmdDevotion {

    private static final CmdDevotion CMD = new CmdDevotion();


    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("devotion")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("get")
                                .then(Commands.argument("deity", new DeityArgumentType())
                                        .executes(ctx -> CMD.getDevotion(ctx, EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("deity", String.class))))
                        )
                        .then(Commands.literal("set")
                                .then(Commands.argument("deity", new DeityArgumentType())
                                        .then(Commands.argument("qt", DoubleArgumentType.doubleArg(0, 60))
                                                .executes(ctx -> CMD.setDevotion(EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("deity", String.class), DoubleArgumentType.getDouble(ctx, "qt")))))
                        )
                );
    }

    private int setDevotion(ServerPlayerEntity player, String deityName, double qt) {

        Deity deity;

        switch (deityName) {
            case ("dark"):
                deity = Deities.DARK_DEITY;
                break;

            case ("blade"):
                deity = HexDeities.HEX_DEITY;
                break;

            default:
                return 0;
        }

        player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
            double prev = rep.getReputation(player, deity.getId());
            rep.setReputation(player, deity.getId(), qt);
            deity.onReputationChange(player, rep, prev, rep.getReputation(player, deity.getId()));
        });

        return Command.SINGLE_SUCCESS;
    }

    private int getDevotion(CommandContext<CommandSource> ctx, ServerPlayerEntity player, String deityName) {

        Deity deity;

        switch (deityName) {
            case ("dark"):
                deity = Deities.DARK_DEITY;
                break;

            case ("blade"):
                deity = HexDeities.HEX_DEITY;
                break;

            default:
                return 0;
        }

        player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
            double devotion = rep.getReputation(player, deity.getId());
            ctx.getSource().sendSuccess(new StringTextComponent("" + devotion), false);
        });

        return Command.SINGLE_SUCCESS;
    }


}
