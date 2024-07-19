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
package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingRecipe;
import io.github.realyusufismail.realyusufismailcore.recipe.EnchantmentRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeSerializerInit {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, RealYusufIsmailCore.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, LegacySmithingRecipe.Serializer> LEGACY_SMITHING =
            simple("legacy_smithing", new LegacySmithingRecipe.Serializer());

    public static final DeferredHolder<RecipeSerializer<?>, EnchantmentRecipe.Serializer> ENCHANTMENT =
            simple("enchantment", new EnchantmentRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> DeferredHolder<RecipeSerializer<?>, S> simple(
            String key, S serializer) {
        return SERIALIZERS.register(key, () -> serializer);
    }
}
