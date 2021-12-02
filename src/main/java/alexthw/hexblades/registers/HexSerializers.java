package alexthw.hexblades.registers;

import alexthw.hexblades.commands.args.DeityArgumentType;
import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.commands.args.KnowledgeArgumentType;
import alexthw.hexblades.recipes.ArmorFocusRecipe;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexSerializers {

    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> evt) {
        IForgeRegistry<RecipeSerializer<?>> reg = evt.getRegistry();
        reg.register(ArmorFocusRecipe.SERIALIZER.setRegistryName(prefix("armor_focus_attach")));
    }

    public static void registerCmdArgTypesSerializers() {
        register(prefix("deity"), DeityArgumentType.class, new EmptyArgumentSerializer<>(DeityArgumentType::new));
        register(prefix("knowledge"), KnowledgeArgumentType.class, new EmptyArgumentSerializer<>(KnowledgeArgumentType::new));
        register(prefix("fact"), FactArgumentType.class, new EmptyArgumentSerializer<>(FactArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void register(ResourceLocation key, Class<T> argumentClazz, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClazz, serializer);
    }
}
