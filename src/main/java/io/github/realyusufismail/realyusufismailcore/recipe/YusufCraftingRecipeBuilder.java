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
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public abstract class YusufCraftingRecipeBuilder {
    protected static CraftingBookCategory determineBookCategory(RecipeCategory p_250736_) {

        return switch (p_250736_) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            default -> CraftingBookCategory.MISC;
        };
    }

    protected abstract static class CraftingResult implements FinishedRecipe {
        private final CraftingBookCategory category;

        protected CraftingResult(CraftingBookCategory pCategory) {
            this.category = pCategory;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("category", this.category.getSerializedName());
        }
    }
}
