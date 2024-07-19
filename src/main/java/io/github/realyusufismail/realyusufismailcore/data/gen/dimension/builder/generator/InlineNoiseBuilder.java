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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.GeneratorBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.*;
import lombok.Getter;
import lombok.val;
import net.minecraft.world.level.block.Block;

@Getter
public class InlineNoiseBuilder {
    private final GeneratorBuilder generatorBuilder;
    private final BiomeSourceBuilder biomeSourceBuilder;

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

    public InlineNoiseBuilder(GeneratorBuilder generatorBuilder, BiomeSourceBuilder biomeSourceBuilder) {
        this.generatorBuilder = generatorBuilder;
        this.biomeSourceBuilder = biomeSourceBuilder;
    }

    public InlineNoiseBuilder setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
        return this;
    }

    public InlineNoiseBuilder setDisableMobGeneration(boolean disableMobGeneration) {
        this.disableMobGeneration = disableMobGeneration;
        return this;
    }

    public InlineNoiseBuilder setAquifersEnabled(boolean aquifersEnabled) {
        this.aquifersEnabled = aquifersEnabled;
        return this;
    }

    public InlineNoiseBuilder setOreVeinsEnabled(boolean oreVeinsEnabled) {
        this.oreVeinsEnabled = oreVeinsEnabled;
        return this;
    }

    public InlineNoiseBuilder setLegacyRandomSource(boolean legacyRandomSource) {
        this.legacyRandomSource = legacyRandomSource;
        return this;
    }

    public InlineNoiseBuilder setDefaultBlock(Block defaultBlock) {
        this.defaultBlock = defaultBlock;
        return this;
    }

    public InlineNoiseBuilder setDefaultFluid(Block defaultFluid) {
        this.defaultFluid = defaultFluid;
        return this;
    }

    public InlineNoiseBuilder setNoise(Noise noise) {
        this.noise = noise;
        return this;
    }

    public InlineNoiseBuilder setNoiseRouter(NoiseRouter noiseRouter) {
        this.noiseRouter = noiseRouter;
        return this;
    }

    public InlineNoiseBuilder setSpawnTarget(SpawnTarget[] spawnTarget) {
        this.spawnTarget = spawnTarget;
        return this;
    }

    public InlineNoiseBuilder setSurfaceRule(SurfaceRule surfaceRule) {
        this.surfaceRule = surfaceRule;
        return this;
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
