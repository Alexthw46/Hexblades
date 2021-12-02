package alexthw.hexblades.commands;

import alexthw.hexblades.Hexblades;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hexblades.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class HexCommands {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(new TranslatableComponent("command.hexblades.error"));

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> cmdHex = dispatcher.register(
                Commands.literal(Hexblades.MODID)
                        .then(CmdDevotion.register(dispatcher))
                        .then(CmdFacts.register(dispatcher))
                        .then(CmdKnowledge.register(dispatcher))
        );

    }
}
