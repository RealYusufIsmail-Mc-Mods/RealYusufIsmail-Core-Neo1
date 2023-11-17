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
import lombok.val;

public class NoiseRouter {
    private int barrier;
    private int fluidLevelFloodedness;
    private int fluidLevelSpread;
    private int lava;
    private int temperature;
    private int vegetation;
    private int continents;
    private int erosion;
    private int depth;
    private int ridges;
    private int initialDensityWithoutJaggedness;
    private FinalDensity finalDensity;
    private int veinToggle;
    private int veinRidged;
    private int veinGap;

    public NoiseRouter setBarrier(int barrier) {
        this.barrier = barrier;
        return this;
    }

    public NoiseRouter setFluidLevelFloodedness(int fluidLevelFloodedness) {
        this.fluidLevelFloodedness = fluidLevelFloodedness;
        return this;
    }

    public NoiseRouter setFluidLevelSpread(int fluidLevelSpread) {
        this.fluidLevelSpread = fluidLevelSpread;
        return this;
    }

    public NoiseRouter setLava(int lava) {
        this.lava = lava;
        return this;
    }

    public NoiseRouter setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public NoiseRouter setVegetation(int vegetation) {
        this.vegetation = vegetation;
        return this;
    }

    public NoiseRouter setContinents(int continents) {
        this.continents = continents;
        return this;
    }

    public NoiseRouter setErosion(int erosion) {
        this.erosion = erosion;
        return this;
    }

    public NoiseRouter setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public NoiseRouter setRidges(int ridges) {
        this.ridges = ridges;
        return this;
    }

    public NoiseRouter setInitialDensityWithoutJaggedness(int initialDensityWithoutJaggedness) {
        this.initialDensityWithoutJaggedness = initialDensityWithoutJaggedness;
        return this;
    }

    public NoiseRouter setFinalDensity(FinalDensity finalDensity) {
        this.finalDensity = finalDensity;
        return this;
    }

    public NoiseRouter setVeinToggle(int veinToggle) {
        this.veinToggle = veinToggle;
        return this;
    }

    public NoiseRouter setVeinRidged(int veinRidged) {
        this.veinRidged = veinRidged;
        return this;
    }

    public NoiseRouter setVeinGap(int veinGap) {
        this.veinGap = veinGap;
        return this;
    }

    public JsonElement toJson() {
        val object = new JsonObject();
        object.addProperty("barrier", barrier);
        object.addProperty("fluid_level_floodedness", fluidLevelFloodedness);
        object.addProperty("fluid_level_spread", fluidLevelSpread);
        object.addProperty("lava", lava);
        object.addProperty("temperature", temperature);
        object.addProperty("vegetation", vegetation);
        object.addProperty("continents", continents);
        object.addProperty("erosion", erosion);
        object.addProperty("depth", depth);
        object.addProperty("ridges", ridges);
        object.addProperty("initial_density_without_jaggedness", initialDensityWithoutJaggedness);
        object.add("final_density", finalDensity.toJson());
        object.addProperty("vein_toggle", veinToggle);
        object.addProperty("vein_ridged", veinRidged);
        object.addProperty("vein_gap", veinGap);
        return object;
    }

    public static class FinalDensity {
        private String type;
        private String argument;

        public FinalDensity setType(String type) {
            this.type = type;
            return this;
        }

        public FinalDensity setArgument(String argument) {
            this.argument = argument;
            return this;
        }

        public JsonElement toJson() {
            JsonObject object = new JsonObject();
            object.addProperty("type", type);
            object.addProperty("argument", argument);
            return object;
        }
    }
}
