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
package io.github.realyusufismail.realyusufismailcore.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.realyusufismailcore.core.init.RecipeSerializerInit;
import io.github.realyusufismail.realyusufismailcore.recipe.pattern.EnchantmentRecipePattern;
import io.github.realyusufismail.realyusufismailcore.recipe.util.EnchantmentsAndLevels;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnchantmentRecipe implements CraftingRecipe {
    final EnchantmentRecipePattern pattern;
    final ItemStack result;
    final String group;
    final CraftingBookCategory category;
    final boolean showNotification;
    final EnchantmentsAndLevels enchantmentsAndLevels;
    final int hideFlags;

    public EnchantmentRecipe(
            String p_272759_,
            CraftingBookCategory p_273506_,
            EnchantmentRecipePattern p_312827_,
            ItemStack p_272852_,
            boolean p_312010_,
            EnchantmentsAndLevels enchantmentsAndLevels,
            int hideFlags) {
        this.group = p_272759_;
        this.category = p_273506_;
        this.pattern = p_312827_;
        this.result = p_272852_;
        this.showNotification = p_312010_;
        this.enchantmentsAndLevels = enchantmentsAndLevels;
        this.hideFlags = hideFlags;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.ENCHANTMENT.get();
    }

    public @NotNull String getGroup() {
        return this.group;
    }

    public @NotNull CraftingBookCategory category() {
        return this.category;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.pattern.getIngredients();
    }

    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        return this.pattern.matches(craftingInput);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return this.getResultItem(provider).copy();
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= this.pattern.getWidth() && pHeight >= this.pattern.getHeight();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty()
                || nonnulllist.stream()
                        .filter(p_151277_ -> !p_151277_.isEmpty())
                        .anyMatch(Ingredient::hasNoItems);
    }

    public int getWidth() {
        return this.pattern.getWidth();
    }

    public int getHeight() {
        return this.pattern.getHeight();
    }

    public static class Serializer implements RecipeSerializer<EnchantmentRecipe> {
        public static final MapCodec<EnchantmentRecipe> CODEC = RecordCodecBuilder.mapCodec(p_340778_ -> p_340778_
                .group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(p_311729_ -> p_311729_.group),
                        CraftingBookCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingBookCategory.MISC)
                                .forGetter((p_311732_) -> p_311732_.category),
                        EnchantmentRecipePattern.MAP_CODEC.forGetter((p_311733_) -> p_311733_.pattern),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_311730_ -> p_311730_.result),
                        Codec.BOOL
                                .optionalFieldOf("show_notification", Boolean.TRUE)
                                .forGetter(p_311731_ -> p_311731_.showNotification),
                        EnchantmentsAndLevels.getCodec().fieldOf("enchantments").forGetter((p_311734_) -> p_311734_
                                .enchantmentsAndLevels),
                        Codec.INT.fieldOf("hide_flags").orElse(0).forGetter((p_311735_) -> p_311735_.hideFlags))
                .apply(p_340778_, EnchantmentRecipe::new));

        public Serializer() {}

        public @NotNull MapCodec<EnchantmentRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EnchantmentRecipe> streamCodec() {
            return StreamCodec.of(EnchantmentRecipe.Serializer::toNetwork, EnchantmentRecipe.Serializer::fromNetwork);
        }

        public static EnchantmentRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
            EnchantmentRecipePattern shapedrecipepattern = EnchantmentRecipePattern.fromNetwork(pBuffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(pBuffer);
            boolean flag = pBuffer.readBoolean();
            EnchantmentsAndLevels enchantmentsAndLevels = pBuffer.readJsonWithCodec(EnchantmentsAndLevels.getCodec());
            int hideFlags = pBuffer.readInt();
            return new EnchantmentRecipe(
                    s, craftingbookcategory, shapedrecipepattern, itemstack, flag, enchantmentsAndLevels, hideFlags);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, EnchantmentRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeEnum(pRecipe.category);
            pRecipe.pattern.toNetwork(pBuffer);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification);
            pRecipe.enchantmentsAndLevels.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.hideFlags);
        }
    }
}
