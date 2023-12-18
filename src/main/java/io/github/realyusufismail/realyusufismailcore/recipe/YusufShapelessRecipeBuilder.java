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

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static io.github.realyusufismail.realyusufismailcore.recipe.YusufCraftingRecipeBuilder.determineBookCategory;

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
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;

    public YusufShapelessRecipeBuilder(RecipeCategory category, @NotNull ItemLike itemLike,
            int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(RecipeCategory category,
            @NotNull ItemLike itemLike) {
        return new YusufShapelessRecipeBuilder(category, itemLike, 1);
    }

    public static @NotNull YusufShapelessRecipeBuilder shapeless(RecipeCategory category,
            @NotNull ItemLike itemLike, int count) {
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

    public @NotNull YusufShapelessRecipeBuilder unlockedBy(@NotNull String creterionId,
            @NotNull CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(creterionId, criterionTriggerInstance);
        return this;
    }

    public @NotNull YusufShapelessRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer,
            @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new ShapelessRecipeBuilder.Result(resourceLocation,
                this.result, this.count, this.group == null ? "" : this.group,
                determineBookCategory(this.category), this.ingredients, this.advancement,
                resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(CraftingBookCategory category, ResourceLocation id, Item result, int count,
            String group, List<Ingredient> ingredients, Advancement.Builder advancement,
            ResourceLocation advancementId) implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonArray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonArray.add(ingredient.toJson());
            }

            jsonObject.add("ingredients", jsonArray);
            JsonObject jsonObjectTwo = new JsonObject();
            jsonObjectTwo.addProperty("item",
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonObjectTwo.addProperty("count", this.count);
            }

            jsonObject.add("result", jsonObjectTwo);
        }

        public @NotNull RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPELESS_RECIPE;
        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
