/*
 * Copyright 2024 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.realyusufismailcore.recipe.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.realyusufismail.realyusufismailcore.recipe.EnchantmentRecipe;
import io.github.realyusufismail.realyusufismailcore.recipe.pattern.EnchantmentRecipePattern;
import io.github.realyusufismail.realyusufismailcore.recipe.util.EnchantmentsAndLevels;
import java.util.*;
import javax.annotation.Nullable;
import lombok.val;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * @see net.minecraft.data.recipes.ShapedRecipeBuilder
 */
@SuppressWarnings("unused")
public class EnchantmentRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private int level;
    private EnchantmentsAndLevels enchantmentsAndLevels = new EnchantmentsAndLevels();
    private int hideFlags = 0;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;

    private boolean showNotification = true;

    public EnchantmentRecipeBuilder(RecipeCategory category, @NotNull ItemLike itemLike, int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static @NotNull EnchantmentRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike) {
        return shaped(category, itemLike, 1);
    }

    public static @NotNull EnchantmentRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike, int count) {
        return new EnchantmentRecipeBuilder(category, itemLike, count);
    }

    public EnchantmentRecipeBuilder define(Character character, TagKey<Item> item) {
        return this.define(character, Ingredient.of(item));
    }

    public EnchantmentRecipeBuilder define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public EnchantmentRecipeBuilder define(Character character, Ingredient ingredient) {
        if (this.key.containsKey(character)) {
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        } else if (character == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(character, ingredient);
            return this;
        }
    }

    public EnchantmentRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @NotNull
    public EnchantmentRecipeBuilder unlockedBy(@NotNull String creterionId, @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public EnchantmentRecipeBuilder enchantment(Enchantment enchantment, int level) {
        this.enchantmentsAndLevels.add(enchantment, level);
        return this;
    }

    @NotNull
    public EnchantmentRecipeBuilder setHideFlags(int hideFlags) {
        this.hideFlags = hideFlags;
        return this;
    }

    public @NotNull EnchantmentRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public EnchantmentRecipeBuilder showNotification(boolean p_273326_) {
        this.showNotification = p_273326_;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation resourceLocation) {
        val pattern = this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput
                .advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        val recipe = new EnchantmentRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                pattern,
                new ItemStack(this.result, this.count),
                this.showNotification,
                this.enchantmentsAndLevels,
                this.hideFlags);

        recipeOutput.accept(
                resourceLocation,
                recipe,
                advancementBuilder.build(
                        resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private EnchantmentRecipePattern ensureValid(ResourceLocation p_126144_) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126144_);
        } else {
            return EnchantmentRecipePattern.of(this.key, this.rows);
        }
    }
}
