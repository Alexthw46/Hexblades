package alexthw.hexblades.deity;

import elucent.eidolon.capability.Facts;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class HexFacts extends Facts {

    public static final ResourceLocation AWAKENING_RITUAL = new ResourceLocation("hexblades", "awakening_ritual");
    public static final ResourceLocation ELEMENTAL_SUMMON = new ResourceLocation("hexblades", "star_infusion"); //can't change for compat with older versions
    public static final ResourceLocation EVOLVE_RITUAL = new ResourceLocation("hexblades", "evolution_ritual");

    public static List<String> Facts = Arrays.asList("awakening_ritual", "evolution_ritual", "elemental_summoning", "villager_sacrifice");


}
