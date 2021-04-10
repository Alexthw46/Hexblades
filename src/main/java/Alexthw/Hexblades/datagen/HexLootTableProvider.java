package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.core.registers.HexBlock;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static Alexthw.Hexblades.core.registers.HexBlock.BLOCKS;
import static Alexthw.Hexblades.core.util.HexUtils.takeAll;

public class HexLootTableProvider extends LootTableProvider {
    public HexLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();
    private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(HexBlock.DEV_BLOCK).map(c -> c.get().asItem()).collect(ImmutableSet.toImmutableSet());

    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {

        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), dropping(b.get().asItem())));

        return tables;

    }


    protected static <T> T withSurvivesExplosion(IItemProvider item, ILootConditionConsumer<T> condition) {
        return (T) (!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.acceptCondition(SurvivesExplosion.builder()) : condition.cast());
    }

    protected static LootTable.Builder dropping(IItemProvider item) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item))));
    }

    protected static LootTable.Builder dropping(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(conditionBuilder).alternatively(p_218494_2_)));
    }

    protected void registerLootTable(Block blockIn, LootTable.Builder table) {
        Hexblades.LOGGER.info(blockIn);
        addTable(blockIn.getLootTable(), table);
    }

    void addTable(ResourceLocation path, LootTable.Builder lootTable) {
        try {
            Hexblades.LOGGER.info(path);
            tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootParameterSets.BLOCK));
        } catch (RuntimeException runtimeException) {
            Hexblades.LOGGER.info(path);
        }
    }

}
