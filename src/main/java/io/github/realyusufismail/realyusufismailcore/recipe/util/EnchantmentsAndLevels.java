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
package io.github.realyusufismail.realyusufismailcore.recipe.util;

import com.mojang.serialization.Codec;
import java.util.*;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnchantmentsAndLevels implements Map<DataComponentType<Enchantment>, Integer> {
    private final Map<DataComponentType<Enchantment>, Integer> enchantmentsAndLevels = new HashMap<>();

    public EnchantmentsAndLevels() {}

    public EnchantmentsAndLevels(Map<DataComponentType<Enchantment>, Integer> enchantmentsAndLevels) {
        this.enchantmentsAndLevels.putAll(enchantmentsAndLevels);
    }

    public void add(DataComponentType<Enchantment> enchantment, int level) {
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
    public Integer put(DataComponentType<Enchantment> key, Integer value) {
        return enchantmentsAndLevels.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return enchantmentsAndLevels.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends DataComponentType<Enchantment>, ? extends Integer> m) {
        enchantmentsAndLevels.putAll(m);
    }

    @Override
    public void clear() {
        enchantmentsAndLevels.clear();
    }

    @NotNull
    @Override
    public Set<DataComponentType<Enchantment>> keySet() {
        return enchantmentsAndLevels.keySet();
    }

    @NotNull
    @Override
    public Collection<Integer> values() {
        return enchantmentsAndLevels.values();
    }

    @NotNull
    @Override
    public Set<Entry<DataComponentType<Enchantment>, Integer>> entrySet() {
        return enchantmentsAndLevels.entrySet();
    }

    public static final Codec<EnchantmentsAndLevels> CODEC = Codec.unboundedMap(
                    Codec.STRING.xmap(
                            name -> BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE.get(ResourceLocation.parse(name)),
                            enchantment -> Objects.requireNonNull(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE.getKey(enchantment))
                                    .toString()),
                    Codec.INT)
            .xmap((dataComponentTypeIntegerMap -> {
                EnchantmentsAndLevels enchantmentsAndLevels = new EnchantmentsAndLevels();
                dataComponentTypeIntegerMap.forEach((dataComponentType, integer) -> enchantmentsAndLevels.put((DataComponentType<Enchantment>) dataComponentType, integer));
                return enchantmentsAndLevels;
            }), enchantmentsAndLevels1 -> new HashMap<>());

    public static Codec<EnchantmentsAndLevels> getCodec() {
        return CODEC;
    }

    public void toNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeVarInt(enchantmentsAndLevels.size());

        for (Map.Entry<DataComponentType<Enchantment>, Integer> entry : enchantmentsAndLevels.entrySet()) {
            pBuffer.writeUtf(Objects.requireNonNull(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE.getKey(entry.getKey()))
                    .toString());
            pBuffer.writeVarInt(entry.getValue());
        }
    }
}
