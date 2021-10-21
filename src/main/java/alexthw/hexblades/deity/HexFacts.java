package alexthw.hexblades.deity;

import elucent.eidolon.capability.Facts;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class HexFacts extends Facts {

    public static final ResourceLocation AWAKENING_RITUAL = new ResourceLocation("hexblades", "awakening_ritual");
    public static final ResourceLocation STAR_INFUSION = new ResourceLocation("hexblades", "star_infusion");
    public static final ResourceLocation EVOLVE_RITUAL = new ResourceLocation("hexblades", "evolution_ritual");

    public static List<String> Facts = Arrays.asList("awakening_ritual", "evolution_ritual", "star_infusion", "villager_sacrifice");


}
