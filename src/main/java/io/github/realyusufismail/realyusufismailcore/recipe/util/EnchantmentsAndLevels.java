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
package io.github.realyusufismail.realyusufismailcore.recipe.util;

import com.mojang.serialization.Codec;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.val;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnchantmentsAndLevels implements Map<Enchantment, Integer> {
    private final Map<Enchantment, Integer> enchantmentsAndLevels = new HashMap<>();

    public EnchantmentsAndLevels() {}

    public void add(Enchantment enchantment, int level) {
        enchantmentsAndLevels.put(enchantment, level);
    }

    public void addAll(EnchantmentsAndLevels otherEnchantmentsAndLevels) {
        enchantmentsAndLevels.putAll(otherEnchantmentsAndLevels.enchantmentsAndLevels);
    }

    @Override
    public int size() {
        return enchantmentsAndLevels.size();
    }

    @Override
    public boolean isEmpty() {
        return enchantmentsAndLevels.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return enchantmentsAndLevels.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return enchantmentsAndLevels.containsValue(value);
    }

    @Override
    public Integer get(Object key) {
        return enchantmentsAndLevels.get(key);
    }

    @Nullable
    @Override
    public Integer put(Enchantment key, Integer value) {
        return enchantmentsAndLevels.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return enchantmentsAndLevels.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Enchantment, ? extends Integer> m) {
        enchantmentsAndLevels.putAll(m);
    }

    @Override
    public void clear() {
        enchantmentsAndLevels.clear();
    }

    @NotNull
    @Override
    public Set<Enchantment> keySet() {
        return enchantmentsAndLevels.keySet();
    }

    @NotNull
    @Override
    public Collection<Integer> values() {
        return enchantmentsAndLevels.values();
    }

    @NotNull
    @Override
    public Set<Entry<Enchantment, Integer>> entrySet() {
        return enchantmentsAndLevels.entrySet();
    }

    public static Codec<EnchantmentsAndLevels> getCodec() {
        val codec = Codec.unboundedMap(Enchantment.CODEC, Codec.INT);
        return codec.xmap(EnchantmentsAndLevels::new, EnchantmentsAndLevels::enchantmentsAndLevels);
    }

    public void toNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeUtf(getCodec().encodeStart(pBuffer.getAllocator(), this).get().toString());
    }
}
