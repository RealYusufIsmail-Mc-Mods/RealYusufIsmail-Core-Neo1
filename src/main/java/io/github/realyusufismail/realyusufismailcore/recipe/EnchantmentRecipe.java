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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.realyusufismailcore.recipe.util.EnchantmentsAndLevels;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class EnchantmentRecipe implements CraftingRecipe, IShapedRecipe<CraftingContainer> {
    final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;
    final CraftingBookCategory category;
    final boolean showNotification;
    final EnchantmentsAndLevels enchantmentsAndLevels;
    final int hideFlags;

    public EnchantmentRecipe(
            String p_272759_,
            CraftingBookCategory p_273506_,
            ShapedRecipePattern p_312827_,
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

    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SHAPED_RECIPE;
    }

    public @NotNull String getGroup() {
        return this.group;
    }

    public int getRecipeWidth() {
        return this.getWidth();
    }

    public CraftingBookCategory category() {
        return this.category;
    }

    public int getRecipeHeight() {
        return this.getHeight();
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    public boolean showNotification() {
        return this.showNotification;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= this.pattern.width() && pHeight >= this.pattern.height();
    }

    public boolean matches(CraftingContainer pInv, Level pLevel) {
        return this.pattern.matches(pInv);
    }

    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.getResultItem(pRegistryAccess).copy();
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty()
                || nonnulllist.stream()
                        .filter((p_151277_) -> {
                            return !p_151277_.isEmpty();
                        })
                        .anyMatch(CommonHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<EnchantmentRecipe> {
        public static final Codec<EnchantmentRecipe> CODEC = RecordCodecBuilder.create((p_311728_) -> p_311728_
                .group(
                        ExtraCodecs.strictOptionalField(Codec.STRING, "group", "")
                                .forGetter((p_311729_) -> p_311729_.group),
                        CraftingBookCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingBookCategory.MISC)
                                .forGetter((p_311732_) -> p_311732_.category),
                        ShapedRecipePattern.MAP_CODEC.forGetter((p_311733_) -> p_311733_.pattern),
                        ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter((p_311730_) -> p_311730_.result),
                        ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true)
                                .forGetter((p_311731_) -> p_311731_.showNotification),
                        EnchantmentsAndLevels.getCodec().fieldOf("enchantments").forGetter((p_311734_) -> p_311734_
                                .enchantmentsAndLevels),
                        Codec.INT.fieldOf("hide_flags").orElse(0).forGetter((p_311735_) -> p_311735_.hideFlags))
                .apply(p_311728_, EnchantmentRecipe::new));

        public Serializer() {}

        public Codec<EnchantmentRecipe> codec() {
            return CODEC;
        }

        public EnchantmentRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            CraftingBookCategory craftingbookcategory =
                    (CraftingBookCategory) pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            boolean flag = pBuffer.readBoolean();
            EnchantmentsAndLevels enchantmentsAndLevels =
                    EnchantmentsAndLevels.getCodec().parse(pBuffer).getOrThrow(false, System.err::println);
            int hideFlags = pBuffer.readInt();
            return new EnchantmentRecipe(
                    s, craftingbookcategory, shapedrecipepattern, itemstack, flag, enchantmentsAndLevels, hideFlags);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, EnchantmentRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeEnum(pRecipe.category);
            pRecipe.pattern.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification);
            pRecipe.enchantmentsAndLevels.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.hideFlags);
        }
    }
}
