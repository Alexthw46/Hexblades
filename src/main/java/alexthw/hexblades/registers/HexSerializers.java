package alexthw.hexblades.registers;

import alexthw.hexblades.commands.args.DeityArgumentType;
import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import alexthw.hexblades.temp.ArmorFocusRecipe;
import alexthw.hexblades.temp.WarlockArmorDye;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexSerializers {

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        IForgeRegistry<IRecipeSerializer<?>> reg = evt.getRegistry();
        reg.register(ArmorFocusRecipe.SERIALIZER.setRegistryName(prefix("armor_focus_attach")));
        reg.register(WarlockArmorDye.SERIALIZER.setRegistryName(prefix("dye_warlock_armor")));
    }

    public static void registerCmdArgTypesSerializers() {
        register(prefix("deity"), DeityArgumentType.class, new ArgumentSerializer<>(DeityArgumentType::new));
        register(prefix("knowledge"), KnowledgeArgumentType.class, new ArgumentSerializer<>(KnowledgeArgumentType::new));
        register(prefix("fact"), FactArgumentType.class, new ArgumentSerializer<>(FactArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void register(ResourceLocation key, Class<T> argumentClazz, IArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClazz, serializer);
    }
}
