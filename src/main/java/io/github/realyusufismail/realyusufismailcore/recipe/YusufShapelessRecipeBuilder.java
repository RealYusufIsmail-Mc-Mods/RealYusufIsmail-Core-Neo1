/*
 * Copyright 2023 RealYusufIsmail.
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
package io.github.realyusufismail.realyusufismailcore.recipe;

import static io.github.realyusufismail.realyusufismailcore.recipe.YusufCraftingRecipeBuilder.determineBookCategory;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Taken from
 *
 * @see ShapelessRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufShapelessRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;

    public YusufShapelessRecipeBuilder(RecipeCategory category, @NotNull ItemLike itemLike, int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(RecipeCategory category, @NotNull ItemLike itemLike) {
        return new YusufShapelessRecipeBuilder(category, itemLike, 1);
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(
            RecipeCategory category, @NotNull ItemLike itemLike, int count) {
        return new YusufShapelessRecipeBuilder(category, itemLike, count);
    }

    public YusufShapelessRecipeBuilder requires(TagKey<Item> itemTag) {
        return this.requires(Ingredient.of(itemTag));
    }

    public YusufShapelessRecipeBuilder requires(ItemLike itemLike) {
        return this.requires(itemLike, 1);
    }

    public YusufShapelessRecipeBuilder requires(ItemLike itemLike, int count) {
        for (int i = 0; i < count; ++i) {
            this.requires(Ingredient.of(itemLike));
        }

        return this;
    }

    public YusufShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public YusufShapelessRecipeBuilder requires(Ingredient ingredient, int count) {
        for (int i = 0; i < count; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public @NotNull YusufShapelessRecipeBuilder unlockedBy(
            @NotNull String creterionId, @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufShapelessRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput
                .advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        recipeOutput.accept(new Result(
                determineBookCategory(this.category),
                resourceLocation,
                this.result,
                this.count,
                this.group == null ? "" : this.group,
                this.ingredients,
                advancementBuilder.build(
                        resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/"))));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(
            CraftingBookCategory category,
            ResourceLocation id,
            Item result,
            int count,
            String group,
            List<Ingredient> ingredients,
            AdvancementHolder advancement)
            implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonArray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonArray.add(ingredient.toJson(false));
            }

            jsonObject.add("ingredients", jsonArray);
            JsonObject jsonObjectTwo = new JsonObject();
            jsonObjectTwo.addProperty(
                    "item",
                    Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(this.result))
                            .toString());
            if (this.count > 1) {
                jsonObjectTwo.addProperty("count", this.count);
            }

            jsonObject.add("result", jsonObjectTwo);
        }

        public @NotNull RecipeSerializer<?> type() {
            return RecipeSerializer.SHAPELESS_RECIPE;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
