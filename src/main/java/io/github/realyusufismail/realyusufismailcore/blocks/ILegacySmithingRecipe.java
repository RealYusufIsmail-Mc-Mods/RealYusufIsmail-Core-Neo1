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
package io.github.realyusufismail.realyusufismailcore.blocks;

import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeTypeInit;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public interface ILegacySmithingRecipe extends Recipe<Container> {
    default @NotNull RecipeType<?> getType() {
        return RecipeTypeInit.LEGACY_SMITHING.get();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    default boolean canCraftInDimensions(int p_266835_, int p_266829_) {
        return p_266835_ >= 3 && p_266829_ >= 1;
    }

    default @NotNull ItemStack getToastSymbol() {
        return new ItemStack(BlockInitCore.LEGACY_SMITHING_TABLE.get());
    }

    boolean isTemplateIngredient(ItemStack p_266982_);

    boolean isBaseIngredient(ItemStack p_266962_);

    boolean isAdditionIngredient(ItemStack p_267132_);
}
