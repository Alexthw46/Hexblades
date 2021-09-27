package alexthw.hexblades;

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
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaAS1;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaAS2;
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
        public final ForgeConfigSpec.ConfigValue<Float> HammerED1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerKB1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerDS2;
        public final ForgeConfigSpec.ConfigValue<Float> HammerED2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerKB2;

        public final ForgeConfigSpec.ConfigValue<Integer> UrnTickRate;

        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Adjust these values to balance the bonus from devotion an Hexblade gets while awakened. Max devotion : 60")
                    .push("Weapon scaling - Sword");

            SwordDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 1.")
                    .define("SwDS1 divide by", 20);
            SwordDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 2.")
                    .define("SwDS2 divide by", 15);
            SwordED1 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the fire sword Tier 1.")
                    .define("SwED1 divide by", 20);
            SwordED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the fire sword Tier 2.")
                    .define("SwED2 divide by", 15);
            builder.pop();

            builder.push("Weapon scaling - Katana");
            KatanaDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the ice katana Tier 1.")
                    .define("KDS1 divide by", 25);
            KatanaDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the ice katana Tier 2.")
                    .define("KDS2 divide by", 15);
            KatanaAS1 = builder
                    .comment("Set the ratio of devotion -> attack speed of the ice katana Tier 1.")
                    .define("KAS1 divide by", 120);
            KatanaAS2 = builder
                    .comment("Set the ratio of devotion -> attack speed of the ice katana Tier 2.")
                    .define("KAS2 divide by", 80);
            KatanaED = builder
                    .comment("Set the ratio of devotion -> elemental damage of the ice katana Tier 1.")
                    .define("KED divide by", 20);
            builder.pop();

            builder.push("Weapon scaling - Saber");
            SaberDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the water saber Tier 1.")
                    .define("SaDS1 divide by", 25);
            SaberSH1 = builder
                    .comment("Set the ratio of devotion -> damage reduction of the water saber Tier 1.")
                    .define("SaSH1 divide by", 20);
            SaberDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the water saber Tier 2.")
                    .define("SaDS2 divide by", 15);
            SaberSH2 = builder
                    .comment("Set the ratio of devotion -> damage reduction of the water saber Tier 2.")
                    .define("SaSH2 divide by", 15);
            SaberED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the water saber Tier 2.")
                    .define("SaED2 divide by", 15);
            builder.pop();

            builder.push("Weapon scaling - Dual");
            DualsDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the thunder duals Tier 1.")
                    .define("DDS1 divide by", 30);
            DualsAS1 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 1.")
                    .define("DAS1 divide by", 60);
            DualsDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the fire sword Tier 2.")
                    .define("DDS2 divide by", 20);
            DualsAS2 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 2.")
                    .define("DAS2 divide by", 40);
            DualsRR = builder
                    .comment("Set the ratio of devotion -> recharge rate of the left thunder dual.")
                    .define("DRR divide by", 2);
            builder.pop();

            builder.push("Weapon scaling - Hammer");
            HammerDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 1.")
                    .define("HDS1 divide by", 20);
            HammerED1 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 1.")
                    .define("HED1 fixed damage", 2.0F);
            HammerMS1 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 1.")
                    .define("HMS1 divide by", 20);
            HammerKB1 = builder
                    .comment("Set the ratio of devotion -> knockback of the earth hammer Tier 1.")
                    .define("HKB1 divide by", 30);
            HammerDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 2.")
                    .define("HDS2 divide by", 15);
            HammerED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 2.")
                    .define("HED2 fixed damage", 2.0F);
            HammerMS2 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 2.")
                    .define("HMS2 divide by", 10);
            HammerKB2 = builder
                    .comment("Set the ratio of devotion -> knockback of the earth hammer Tier 2.")
                    .define("HKB2 divide by", 20);
            builder.pop();

            builder.push("MISC Configs");
            UrnTickRate = builder.
                    comment("Set the cooldown of the Urn of Endless Waters")
                    .define("Refill every x ticks", 100);
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
