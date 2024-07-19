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

import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LegacySmithingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final Item result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final RecipeSerializer<?> type;
    private String group;

    public LegacySmithingRecipeBuilder(
            RecipeCategory category, RecipeSerializer<?> type, Ingredient base, Ingredient addition, Item result) {
        this.category = category;
        this.type = type;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static @NotNull LegacySmithingRecipeBuilder smithing(
            RecipeCategory category, Ingredient base, Ingredient addition, Item result) {
        return new LegacySmithingRecipeBuilder(
                category, RecipeSerializerInit.LEGACY_SMITHING.get(), base, addition, result);
    }

    public LegacySmithingRecipeBuilder unlocks(String creterionId, Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public void save(RecipeOutput recipeOutput, String resourceLocation) {
        this.save(recipeOutput, new ResourceLocation(resourceLocation));
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String pCriterionName, @NotNull Criterion<?> criterion) {
        criteria.put(pCriterionName, criterion);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result;
    }

    public void save(@NotNull RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput
                .advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);
        /*
               recipeOutput.accept(new Result(
                       resourceLocation,
                       this.type,
                       this.base,
                       this.addition,
                       this.result,
                       advancementBuilder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")),
                       group));

        */
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }
}
