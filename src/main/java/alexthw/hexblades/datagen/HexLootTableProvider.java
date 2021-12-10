package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexBlock;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static alexthw.hexblades.registers.HexBlock.BLOCKS;
import static alexthw.hexblades.util.HexUtils.takeAll;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class HexLootTableProvider extends LootTableProvider {
    public HexLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();
    private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(HexBlock.SWORD_STAND).map(c -> c.get().asItem()).collect(ImmutableSet.toImmutableSet());

    private static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
    private static final LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker) {
        map.forEach((loc, table) -> LootTables.validate(validationtracker, loc, table));
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {

        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), dropping(b.get().asItem())));

        return tables;

    }


    protected static <T> T withSurvivesExplosion(ItemLike item, ConditionUserBuilder<T> condition) {
        return !IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.when(ExplosionCondition.survivesExplosion()) : condition.unwrap();
    }

    protected static LootTable.Builder dropping(ItemLike item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item))));
    }

    protected static LootTable.Builder dropping(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)));
    }

    protected void registerLootTable(Block blockIn, LootTable.Builder table) {
        Hexblades.LOGGER.info(blockIn);
        addTable(blockIn.getLootTable(), table);
    }

    void addTable(ResourceLocation path, LootTable.Builder lootTable) {
        try {
            Hexblades.LOGGER.info(path);
            tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootContextParamSets.BLOCK));
        } catch (RuntimeException runtimeException) {
            Hexblades.LOGGER.info(path);
        }
    }

    @Override
    public @NotNull String getName() {
        return "HexBlades Loot Tables";
    }
}
