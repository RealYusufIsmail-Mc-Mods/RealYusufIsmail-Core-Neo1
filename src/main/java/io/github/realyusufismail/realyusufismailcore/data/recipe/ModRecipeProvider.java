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
package io.github.realyusufismail.realyusufismailcore.data.recipe;

import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn.getPackOutput());
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(RealYusufIsmailCore.MOD_ID, path);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInitCore.LEGACY_SMITHING_TABLE.get())
            .define('#', Items.OAK_PLANKS)
            .define('X', Tags.Items.INGOTS_IRON)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_planks", has(Items.OAK_PLANKS))
            .save(consumer);

    }
}
