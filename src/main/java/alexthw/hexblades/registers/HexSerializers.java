package alexthw.hexblades.registers;

import alexthw.hexblades.commands.args.DeityArgumentType;
import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import alexthw.hexblades.temp.ArmorFocusRecipe;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static alexthw.hexblades.datagen.HexItemModelProvider.rl;
import static alexthw.hexblades.util.HexUtils.prefix;

public class HexSerializers {

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        IForgeRegistry<IRecipeSerializer<?>> reg = evt.getRegistry();
        reg.register(ArmorFocusRecipe.SERIALIZER.setRegistryName(prefix("armor_focus_attach")));
    }

    public static void registerCmdArgTypesSerializers() {
        register(rl("deity"), DeityArgumentType.class, new ArgumentSerializer<>(DeityArgumentType::new));
        register(rl("knowledge"), KnowledgeArgumentType.class, new ArgumentSerializer<>(KnowledgeArgumentType::new));
        register(rl("constellation"), FactArgumentType.class, new ArgumentSerializer<>(FactArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void register(ResourceLocation key, Class<T> argumentClazz, IArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClazz, serializer);
    }
}
