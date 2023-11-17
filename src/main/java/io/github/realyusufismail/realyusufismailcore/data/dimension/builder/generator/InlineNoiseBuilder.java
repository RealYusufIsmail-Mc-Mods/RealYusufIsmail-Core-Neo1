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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.GeneratorBuilder;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.builder.*;
import lombok.Getter;
import lombok.val;
import net.minecraft.world.level.block.Block;

@Getter
public class InlineNoiseBuilder {
    private final GeneratorBuilder generatorBuilder;
    private final BiomeSource biomeSource;

    private int seaLevel;
    private boolean disableMobGeneration;
    private boolean aquifersEnabled;
    private boolean oreVeinsEnabled;
    private boolean legacyRandomSource;
    private Block defaultBlock;
    private Block defaultFluid;
    private Noise noise;
    private NoiseRouter noiseRouter;
    private SpawnTarget[] spawnTarget;
    private SurfaceRule surfaceRule;

    public InlineNoiseBuilder(GeneratorBuilder generatorBuilder, BiomeSource biomeSource) {
        this.generatorBuilder = generatorBuilder;
        this.biomeSource = biomeSource;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("sea_level", seaLevel);
        object.addProperty("disable_mob_generation", disableMobGeneration);
        object.addProperty("aquifers_enabled", aquifersEnabled);
        object.addProperty("ore_veins_enabled", oreVeinsEnabled);
        object.addProperty("legacy_random_source", legacyRandomSource);

        val defaultBlock = new JsonObject();
        defaultBlock.addProperty("name", this.defaultBlock.toString());
        object.add("default_block", defaultBlock);

        val defaultFluid = new JsonObject();
        defaultFluid.addProperty("name", this.defaultFluid.toString());
        object.add("default_fluid", defaultFluid);
        object.add("noise", noise.toJson());
        object.add("noise_router", noiseRouter.toJson());

        JsonArray spawnTargetArray = new JsonArray();
        for (SpawnTarget target : spawnTarget) {
            spawnTargetArray.add(target.toJson());
        }
        object.add("spawn_target", spawnTargetArray);

        object.add("surface_rule", surfaceRule.toJson());

        return object;
    }

    public void build() {
        generatorBuilder.setInlineNoiseBuilder(this);
    }
}
