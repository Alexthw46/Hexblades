package alexthw.hexblades;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> SwordBD1;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordBD2;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordED1;
        public final ForgeConfigSpec.ConfigValue<Integer> SwordED2;
        //Katana
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaBD1;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaBD2;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaAS1;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaAS2;
        public final ForgeConfigSpec.ConfigValue<Integer> KatanaED;
        //Saber
        public final ForgeConfigSpec.ConfigValue<Integer> SaberBD1;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberBD2;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberSH1;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberSH2;
        public final ForgeConfigSpec.ConfigValue<Integer> SaberED2;
        //Duals
        public final ForgeConfigSpec.ConfigValue<Integer> DaggerBDR;
        public final ForgeConfigSpec.ConfigValue<Integer> DaggerBDL;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsDS1;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsAS1;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsDS2;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsAS2;
        public final ForgeConfigSpec.ConfigValue<Integer> DualsRR;
        //Ham1
        public final ForgeConfigSpec.ConfigValue<Integer> HammerBD1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerDS1;
        public final ForgeConfigSpec.ConfigValue<Double> HammerED1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS1;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerKB1;
        //Ham2
        public final ForgeConfigSpec.ConfigValue<Integer> HammerBD2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerDS2;
        public final ForgeConfigSpec.ConfigValue<Double> HammerED2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerMS2;
        public final ForgeConfigSpec.ConfigValue<Integer> HammerKB2;

        //Sap
        public final ForgeConfigSpec.ConfigValue<Integer> BloodBD;
        public final ForgeConfigSpec.ConfigValue<Integer> BloodDS;
        public final ForgeConfigSpec.ConfigValue<Integer> BloodED;
        //Misc
        public final ForgeConfigSpec.ConfigValue<Integer> UrnTickRate;

        public final ForgeConfigSpec.ConfigValue<Integer> FT_AVG;
        public final ForgeConfigSpec.ConfigValue<Integer> FT_MIN;
        //Recipes
        public final ForgeConfigSpec.ConfigValue<Boolean> NUKE_RITUALS;
        public final ForgeConfigSpec.ConfigValue<Boolean> NUKE_CRUCIBLE;
        public final ForgeConfigSpec.ConfigValue<Boolean> NUKE_WORKBENCH;


        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Adjust these values to balance the bonus from devotion an Hexblade gets while awakened. Max devotion : 60")
                    .push("Weapon scaling - Sword");

            SwordBD1 = builder
                    .comment("Set the base damage of the fire sword Tier 1")
                    .define("Sword T1 base damage", 6);
            SwordBD2 = builder
                    .comment("Set the base damage of the fire sword Tier 2")
                    .define("Sword T2 base damage", 7);
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

            KatanaBD1 = builder
                    .comment("Set the base damage of the ice katana Tier 1")
                    .define("Katana T1 base damage", 5);
            KatanaBD2 = builder
                    .comment("Set the base damage of the ice katana Tier 2")
                    .define("Katana T2 base damage", 5);
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
            SaberBD1 = builder
                    .comment("Set the base damage of the water saber Tier 1")
                    .define("Saber T1 base damage", 5);
            SaberBD2 = builder
                    .comment("Set the base damage of the water saber Tier 2")
                    .define("Saber T2 base damage", 6);
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

            DaggerBDL = builder
                    .comment("Set the base damage of the left dagger")
                    .define("Left dagger base damage", 1);
            DaggerBDR = builder
                    .comment("Set the base damage of the right dagger")
                    .define("Right dagger base damage", 4);
            DualsDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the thunder duals Tier 1.")
                    .define("DDS1 divide by", 30);
            DualsAS1 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 1.")
                    .define("DAS1 divide by", 60);
            DualsDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the thunder duals Tier 2.")
                    .define("DDS2 divide by", 20);
            DualsAS2 = builder
                    .comment("Set the ratio of devotion -> attack speed of the thunder duals Tier 2.")
                    .define("DAS2 divide by", 40);
            DualsRR = builder
                    .comment("Set the ratio of devotion -> recharge rate of the left thunder dual.")
                    .define("DRR divide by", 10);
            builder.pop();

            builder.push("Weapon scaling - Hammer");

            HammerBD1 = builder
                    .comment("Set the base damage of the earth hammer Tier 1")
                    .define("Hammer T1 base damage", 7);
            HammerDS1 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 1.")
                    .define("HDS1 divide by", 20);
            HammerED1 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 1.")
                    .define("HED1 fixed damage", 2.0);
            HammerMS1 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 1.")
                    .define("HMS1 divide by", 20);
            HammerKB1 = builder
                    .comment("Set the ratio of devotion -> knockback of the earth hammer Tier 1.")
                    .define("HKB1 divide by", 30);
            HammerBD2 = builder
                    .comment("Set the base damage of the earth hammer Tier 2")
                    .define("Hammer T2 base damage", 8);
            HammerDS2 = builder
                    .comment("Set the ratio of devotion -> attack power of the earth hammer Tier 2.")
                    .define("HDS2 divide by", 15);
            HammerED2 = builder
                    .comment("Set the ratio of devotion -> elemental damage of the earth hammer Tier 2.")
                    .define("HED2 fixed damage", 2.0);
            HammerMS2 = builder
                    .comment("Set the ratio of devotion -> mining speed of the earth hammer Tier 2.")
                    .define("HMS2 divide by", 10);
            HammerKB2 = builder
                    .comment("Set the ratio of devotion -> knockback of the earth hammer Tier 2.")
                    .define("HKB2 divide by", 20);
            builder.pop();

            builder.push("Weapon Scaling - Sapping");

            BloodBD = builder
                    .comment("Set the base damage of the upgraded Sapping Sword")
                    .define("Blood Sword base damage", 4);
            BloodDS = builder.comment("Set the ratio of devotion -> attack power of the Sapping sword upgrade")
                    .define("BSWS divide by", 30);
            BloodED = builder.comment("Set the ratio of devotion -> wither damage of the Sapping sword upgrade")
                    .define("BSED divide by", 30);

            builder.pop();

            builder.push("MISC Configs");
            UrnTickRate = builder.
                    comment("Set the cooldown of the Urn of Endless Waters")
                    .define("Refill every x ticks", 100);
            builder.pop();

            builder.comment("WorldGen Configs").push("Fire Temple worldgen");

            FT_AVG = builder.comment("Average distance apart in chunks between spawn attempts")
                    .define("Must be more than minimum", 40);
            FT_MIN = builder.comment("Minimum distance apart in chunks between spawn attempts")
                    .define("Must be less than average", 15);

            builder.pop();

            builder.comment("Recipe disabler for hexblades").push("Recipe nukers");

            NUKE_CRUCIBLE = builder.comment("set this to true to disable all hexblades crucible craftings")
                    .define("Disable crucible recipes", false);

            NUKE_RITUALS = builder.comment("set this to true to disable all hexblades ritual craftings")
                    .define("Disable ritual recipes", false);

            NUKE_WORKBENCH = builder.comment("set this to true to disable all hexblades magic workbench craftings")
                    .define("Disable workbench recipes", false);

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
