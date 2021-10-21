package alexthw.hexblades.commands;

import alexthw.hexblades.Hexblades;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hexblades.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class HexCommands {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(new TranslationTextComponent("command.hexblades.error"));

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdHex = dispatcher.register(
                Commands.literal(Hexblades.MODID)
                        .then(CmdDevotion.register(dispatcher))
                        .then(CmdFacts.register(dispatcher))
                        .then(CmdKnowledge.register(dispatcher))
        );

    }
}
