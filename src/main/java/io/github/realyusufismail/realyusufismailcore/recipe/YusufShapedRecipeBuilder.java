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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.*;
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
 * @see ShapedRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufShapedRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;

    private boolean showNotification = true;

    public YusufShapedRecipeBuilder(RecipeCategory category, ItemLike itemLike, int count) {
        this.category = category;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static YusufShapedRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike) {
        return shaped(category, itemLike, 1);
    }

    public static YusufShapedRecipeBuilder shaped(RecipeCategory category, ItemLike itemLike, int count) {
        return new YusufShapedRecipeBuilder(category, itemLike, count);
    }

    public YusufShapedRecipeBuilder define(Character character, TagKey<Item> itemTag) {
        return this.define(character, Ingredient.of(itemTag));
    }

    public YusufShapedRecipeBuilder define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public YusufShapedRecipeBuilder define(Character character, Ingredient ingredient) {
        if (this.key.containsKey(character)) {
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        } else if (character == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(character, ingredient);
            return this;
        }
    }

    public YusufShapedRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    public @NotNull YusufShapedRecipeBuilder unlockedBy(@NotNull String creterionId, @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufShapedRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public YusufShapedRecipeBuilder showNotification(boolean p_273326_) {
        this.showNotification = p_273326_;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
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
                this.rows,
                this.key,
                advancementBuilder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")),
                this.showNotification));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + resourceLocation + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String s : this.rows) {
                for (int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException(
                                "Pattern in recipe " + resourceLocation + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException(
                        "Ingredients are defined but not used in pattern for recipe " + resourceLocation);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + resourceLocation
                        + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
            }
        }
    }

    public record Result(
            CraftingBookCategory category,
            ResourceLocation id,
            Item result,
            int count,
            String group,
            List<String> pattern,
            Map<Character, Ingredient> key,
            AdvancementHolder advancement,
            boolean showNotification)
            implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.addProperty("category", this.category.getSerializedName());
            JsonArray jsonarray = new JsonArray();

            for (String s : this.pattern) {
                jsonarray.add(s);
            }

            jsonObject.add("pattern", jsonarray);
            JsonObject jsonObject1 = new JsonObject();

            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonObject1.add(String.valueOf(entry.getKey()), entry.getValue().toJson(false));
            }

            jsonObject.add("key", jsonObject1);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(
                    "item",
                    Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(this.result))
                            .toString());
            if (this.count > 1) {
                jsonObject2.addProperty("count", this.count);
            }

            jsonObject.add("result", jsonObject2);
            jsonObject.addProperty("show_notification", this.showNotification);
        }

        public @NotNull RecipeSerializer<?> type() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
