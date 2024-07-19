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
package io.github.realyusufismail.realyusufismailcore.recipe.pattern;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @see net.minecraft.world.item.crafting.ShapedRecipePattern
 */
@Getter
public class EnchantmentRecipePattern {
    static int maxWidth, maxHeight = 3;
    private final int ingredientCount;
    private final boolean symmetrical;
    private final int width, height;
    private final NonNullList<Ingredient> ingredients;
    private final Optional<Data> data;

    public EnchantmentRecipePattern(int width, int height, NonNullList<Ingredient> ingredients, Optional<Data> data) {
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.data = data;
        int count = 0;

        for (Ingredient ingredient : ingredients) {
            if (!ingredient.isEmpty()) {
                count++;
            }
        }

        this.ingredientCount = count;
        this.symmetrical = Util.isSymmetrical(width, height, ingredients);
    }

    /**
     * Expand the max width and height allowed in the deserializer.
     * This should be called by modders who add custom crafting tables that are larger than the vanilla 3x3.
     * @param width your max recipe width
     * @param height your max recipe height
     */
    public static void setCraftingSize(int width, int height) {
        if (maxWidth < width) maxWidth = width;
        if (maxHeight < height) maxHeight = height;
    }

    public static final MapCodec<EnchantmentRecipePattern> MAP_CODEC =
            EnchantmentRecipePattern.Data.MAP_CODEC.flatXmap(EnchantmentRecipePattern::unpack, p_311830_ -> p_311830_
                    .data
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked recipe")));

    private static DataResult<EnchantmentRecipePattern> unpack(EnchantmentRecipePattern.Data p_312037_) {
        String[] astring = shrink(p_312037_.pattern);
        int i = astring[0].length();
        int j = astring.length;
        NonNullList<Ingredient> nonnulllist = net.minecraft.core.NonNullList.withSize(i * j, Ingredient.EMPTY);
        CharSet charset = new CharArraySet(p_312037_.key.keySet());

        for (int k = 0; k < astring.length; ++k) {
            String s = astring[k];

            for (int l = 0; l < s.length(); ++l) {
                char c0 = s.charAt(l);
                Ingredient ingredient = c0 == ' ' ? Ingredient.EMPTY : p_312037_.key.get(c0);
                if (ingredient == null) {
                    return DataResult.error(
                            () -> "Pattern references symbol '" + c0 + "' but it's not defined in the key");
                }

                charset.remove(c0);
                nonnulllist.set(l + i * k, ingredient);
            }
        }

        return !charset.isEmpty()
                ? DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + charset)
                : DataResult.success(new EnchantmentRecipePattern(i, j, nonnulllist, Optional.of(p_312037_)));
    }

    @VisibleForTesting
    static String[] shrink(List<String> p_311893_) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for (int i1 = 0; i1 < p_311893_.size(); ++i1) {
            String s = p_311893_.get(i1);
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (p_311893_.size() == l) {
            return new String[0];
        } else {
            String[] astring = new String[p_311893_.size() - l - k];

            for (int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = p_311893_.get(k1 + k).substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String p_312343_) {
        int i = 0;

        while (i < p_312343_.length() && p_312343_.charAt(i) == ' ') {
            ++i;
        }

        return i;
    }

    private static int lastNonSpace(String p_311944_) {
        int i = p_311944_.length() - 1;

        while (i >= 0 && p_311944_.charAt(i) == ' ') {
            --i;
        }

        return i;
    }

    public boolean matches(CraftingInput pInput) {
        if (pInput.ingredientCount() == this.ingredientCount) {
            if (pInput.width() == this.width && pInput.height() == this.height) {
                if (!this.symmetrical && this.matches(pInput, true)) {
                    return true;
                }

                return this.matches(pInput, false);
            }
        }
        return false;
    }

    private boolean matches(CraftingInput pInput, boolean pSymmetrical) {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Ingredient ingredient;
                if (pSymmetrical) {
                    ingredient = this.ingredients.get(this.width - j - 1 + i * this.width);
                } else {
                    ingredient = this.ingredients.get(j + i * this.width);
                }

                ItemStack itemstack = pInput.getItem(j, i);
                if (!ingredient.test(itemstack)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void toNetwork(RegistryFriendlyByteBuf p_312479_) {
        p_312479_.writeVarInt(this.width);
        p_312479_.writeVarInt(this.height);

        for (Ingredient ingredient : this.ingredients) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(p_312479_, ingredient);
        }
    }

    public static EnchantmentRecipePattern fromNetwork(RegistryFriendlyByteBuf p_312006_) {
        int i = p_312006_.readVarInt();
        int j = p_312006_.readVarInt();
        NonNullList<Ingredient> nonnulllist = net.minecraft.core.NonNullList.withSize(i * j, Ingredient.EMPTY);
        nonnulllist.replaceAll(p_312742_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(p_312006_));
        return new EnchantmentRecipePattern(i, j, nonnulllist, java.util.Optional.empty());
    }

    public static record Data(Map<Character, Ingredient> key, List<String> pattern) {
        private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING
                .listOf()
                .comapFlatMap(
                        p_312085_ -> {
                            if (p_312085_.size() > maxHeight) {
                                return DataResult.error(
                                        () -> "Invalid pattern: too many rows, %s is maximum".formatted(maxHeight));
                            } else if (p_312085_.isEmpty()) {
                                return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
                            } else {
                                int i = p_312085_.get(0).length();

                                for (String s : p_312085_) {
                                    if (s.length() > maxWidth) {
                                        return DataResult.error(() ->
                                                "Invalid pattern: too many columns, %s is maximum".formatted(maxWidth));
                                    }

                                    if (i != s.length()) {
                                        return DataResult.error(
                                                () -> "Invalid pattern: each row must be the same width");
                                    }
                                }

                                return DataResult.success(p_312085_);
                            }
                        },
                        Function.identity());
        private static final Codec<Character> SYMBOL_CODEC = Codec.STRING.comapFlatMap(
                p_312250_ -> {
                    if (p_312250_.length() != 1) {
                        return DataResult.error(() -> "Invalid key entry: '" + p_312250_
                                + "' is an invalid symbol (must be 1 character only).");
                    } else {
                        return " ".equals(p_312250_)
                                ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.")
                                : DataResult.success(p_312250_.charAt(0));
                    }
                },
                String::valueOf);
        public static final MapCodec<EnchantmentRecipePattern.Data> MAP_CODEC =
                RecordCodecBuilder.mapCodec(p_312573_ -> p_312573_
                        .group(
                                ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC, Ingredient.CODEC_NONEMPTY)
                                        .fieldOf("key")
                                        .forGetter(p_312509_ -> p_312509_.key),
                                PATTERN_CODEC.fieldOf("pattern").forGetter(p_312713_ -> p_312713_.pattern))
                        .apply(p_312573_, EnchantmentRecipePattern.Data::new));
    }
}
