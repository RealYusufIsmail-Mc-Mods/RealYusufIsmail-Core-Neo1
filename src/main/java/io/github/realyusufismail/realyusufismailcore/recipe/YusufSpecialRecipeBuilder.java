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

import com.google.gson.JsonObject;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YusufSpecialRecipeBuilder {
    final RecipeSerializer<?> serializer;

    public YusufSpecialRecipeBuilder(RecipeSerializer<?> simpleRecipeSerializer) {
        this.serializer = simpleRecipeSerializer;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull YusufSpecialRecipeBuilder special(RecipeSerializer<?> simpleRecipeSerializer) {
        return new YusufSpecialRecipeBuilder(simpleRecipeSerializer);
    }

    public void save(@NotNull RecipeOutput recipeOutput, final String resourceLocation) {

        recipeOutput.accept(new FinishedRecipe() {
            public void serializeRecipeData(@NotNull JsonObject jsonObject) {
                // TODO document why this method is empty
            }

            @Override
            public ResourceLocation id() {
                return advancement().id();
            }

            public @NotNull RecipeSerializer<?> type() {
                return YusufSpecialRecipeBuilder.this.serializer;
            }

            @Nullable
            @Override
            public AdvancementHolder advancement() {
                return null;
            }
        });
    }
}
