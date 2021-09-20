package Alexthw.Hexblades;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> SwordDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordED1;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordED2;


        public final ForgeConfigSpec.ConfigValue<Integer> KatanaDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaED;

        public final ForgeConfigSpec.ConfigValue<Integer> SaberDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberSH1;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberSH2;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberED2;


        public final ForgeConfigSpec.ConfigValue<Integer> DualsDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsAS1;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsAS2;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsRR;


        public final ForgeConfigSpec.ConfigValue<Integer> HammerDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerED1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerED2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS2;


        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Weapon scaling - Sword");
            SwordDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 1.")
                    .define("divide by", 20);
            SwordDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 2.")
                    .define("divide by", 15);
            SwordED1 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the fire sword Tier 1.")
                    .define("divide by", 20);
            SwordED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the fire sword Tier 2.")
                    .define("divide by", 15);
            builder.pop();

            builder.push("Weapon scaling - Katana");
            KatanaDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the ice katana Tier 1.")
                    .define("divide by", 25);
            KatanaDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the ice katana Tier 2.")
                    .define("divide by", 15);
            KatanaED = builder
                    .comment("Set the ratio of devotion -> elemental damage of the ice katana Tier 1.")
                    .define("divide by", 20);
            builder.pop();

            builder.push("Weapon scaling - Saber");
            SaberDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the water saber Tier 1.")
                    .define("divide by", 25);
            SaberSH1 = builder
                    .comment("Set the ratio of devotion -> damage reduction of the water saber Tier 1.")
                    .define("divide by", 20);
            SaberDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the water saber Tier 2.")
                    .define("divide by", 15);
            SaberSH2 = builder
                    .comment("Set the ratio of devotion -> damage reduction of the water saber Tier 2.")
                    .define("divide by", 15);
            SaberED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the water saber Tier 2.")
                    .define("divide by", 15);
            builder.pop();

            builder.push("Weapon scaling - Dual");
            DualsDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the thunder duals Tier 1.")
                    .define("divide by", 30);
            DualsAS1 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 1.")
                    .define("divide by", 60);
            DualsDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 2.")
                    .define("divide by", 20);
            DualsAS2 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 2.")
                    .define("divide by", 40);
            DualsRR = builder
                    .comment("Set the ratio of devotion -> recharge rate of the left thunder dual.")
                    .define("divide by", 2);
            builder.pop();

            builder.push("Weapon scaling - Hammer");
            HammerDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 1.")
                    .define("divide by", 20);
            HammerED1 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 1.")
                    .define("divide by", 20);
            HammerMS1 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 1.")
                    .define("divide by", 20);
            HammerDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 2.")
                    .define("divide by", 15);
            HammerED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 2.")
                    .define("divide by", 20);
            HammerMS2 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 2.")
                    .define("divide by", 10);
            builder.pop();

        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

}
