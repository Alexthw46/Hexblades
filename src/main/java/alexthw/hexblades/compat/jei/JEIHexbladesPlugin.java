package alexthw.hexblades.compat.jei;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.temp.ArmorFocusRecipe;
import alexthw.hexblades.temp.WarlockArmorDye;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIHexbladesPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(Hexblades.MODID, "main");

    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(ArmorFocusRecipe.class, ArmorFocusRecipeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(WarlockArmorDye.class, WarlockArmorDyeWrapper::new);

    }

}
