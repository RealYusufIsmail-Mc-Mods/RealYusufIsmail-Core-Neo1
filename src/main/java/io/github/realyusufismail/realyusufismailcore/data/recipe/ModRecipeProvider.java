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
package io.github.realyusufismail.realyusufismailcore.data.recipe;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.recipe.builder.EnchantmentRecipeBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generatorIn.getPackOutput(), lookupProvider);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(RealYusufIsmailCore.MOD_ID, path);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInitCore.LEGACY_SMITHING_TABLE.get())
                .define('#', Items.OAK_PLANKS)
                .define('X', Tags.Items.INGOTS_IRON)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_planks", has(Items.OAK_PLANKS))
                .save(consumer);

        EnchantmentRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.DIAMOND_SWORD)
                .define('#', Items.OAK_PLANKS)
                .define('X', Tags.Items.INGOTS_IRON)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .enchantment(Enchantments.SHARPNESS, 1)
                .unlockedBy("has_planks", has(Items.OAK_PLANKS))
                .save(consumer, modId("enchantment_recipe"));
    }
}
