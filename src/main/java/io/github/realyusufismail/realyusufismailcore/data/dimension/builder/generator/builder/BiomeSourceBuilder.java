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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeSourceBuilder {

    private ResourceKey<Biome> biome = Biomes.PLAINS;
    private int scale = 2;
    private List<ResourceKey<Biome>> biomes = new ArrayList<>();

    private boolean hasFixed = false;
    private boolean hasCheckerboard = false;
    private boolean hasEnd = false;

    public BiomeSourceBuilder fixed(ResourceKey<Biome> biome) {
        this.biome = biome;
        this.hasFixed = true;
        return this;
    }

    public BiomeSourceBuilder checkerboard(int scale, List<ResourceKey<Biome>> biomes) {
        this.scale = scale;
        this.biomes = biomes;
        this.hasCheckerboard = true;
        return this;
    }

    public JsonElement toJson() {
        val object = new JsonObject();

        if (hasFixed) {
            object.addProperty("type", BiomeSource.FIXED.getName());
            object.addProperty("biome", biome.location().toString());
        } else if (hasCheckerboard) {
            object.addProperty("type", BiomeSource.CHECKERBOARD.getName());
            object.addProperty("scale", scale);
            val biomes = new ArrayList<>(this.biomes);
            val objectBiomes = new JsonObject();

            if (biomes.size() == 1) {
                object.addProperty("biome", biomes.get(0).location().toString());
            } else {
                for (int i = 0; i < biomes.size(); i++) {
                    objectBiomes.addProperty(
                            String.valueOf(i), biomes.get(i).location().toString());
                }

                object.add("biomes", objectBiomes);
            }

        } else if (hasEnd) {
            object.addProperty("type", BiomeSource.END.getName());
        }

        return object;
    }
}
