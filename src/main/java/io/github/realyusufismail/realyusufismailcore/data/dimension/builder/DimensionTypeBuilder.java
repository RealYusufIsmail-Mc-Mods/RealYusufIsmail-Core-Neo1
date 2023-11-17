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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.util.Effect;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class DimensionTypeBuilder {
    private final String name;

    private boolean isUltrawarm = false;
    private boolean isNatural = false;
    private boolean isPiglinSafe = false;
    private boolean respawnAnchorWorks = false;
    private boolean bedWorks = false;
    private boolean hasRaids = false;
    private boolean hasSkylight = false;
    private boolean hasCeiling = false;
    private int coordinateScale = 0;
    private int ambientLight = 0;
    private int fixedTime = 0;
    private float logicalHeight = 0;
    private Effect effects = Effect.OVERWORLD;
    private String infiniburn = "minecraft:infiniburn_overworld";
    private double minY = 0;
    private double height = 0;
    private Map<String, Map<Integer, Integer>> monsterSpawnLightLevel = new HashMap<>();
    private int monsterSpawnBlockLightLimit = 0;

    public DimensionTypeBuilder(String name) {
        this.name = name;
    }

    public JsonElement toJson() {
        JsonObject json = getJsonObject();

        JsonObject monsterSpawnLightLevelJson = new JsonObject();
        for (Map.Entry<String, Map<Integer, Integer>> entry : this.monsterSpawnLightLevel.entrySet()) {
            JsonObject monsterSpawnLightLevelEntryJson = getJsonObject(entry);
            monsterSpawnLightLevelJson.add(entry.getKey(), monsterSpawnLightLevelEntryJson);
        }
        json.add("monster_spawn_light_level", monsterSpawnLightLevelJson);

        return json;
    }

    /**
     * Sets the 'ultrawarm' flag of the DimensionTypeBuilder.
     *
     * @param ultrawarm if water will evaporate in this dimension and sponge will dry
     * @return the DimensionTypeBuilder instance
     */
    public DimensionTypeBuilder setUltrawarm(boolean ultrawarm) {
        isUltrawarm = ultrawarm;
        return this;
    }

    public DimensionTypeBuilder setNatural(boolean natural) {
        isNatural = natural;
        return this;
    }

    public DimensionTypeBuilder setPiglinSafe(boolean piglinSafe) {
        isPiglinSafe = piglinSafe;
        return this;
    }

    public DimensionTypeBuilder setRespawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }

    public DimensionTypeBuilder setBedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    public DimensionTypeBuilder setHasRaids(boolean hasRaids) {
        this.hasRaids = hasRaids;
        return this;
    }

    public DimensionTypeBuilder setHasSkylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    public DimensionTypeBuilder setHasCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    public DimensionTypeBuilder setCoordinateScale(int coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    public DimensionTypeBuilder setAmbientLight(int ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public DimensionTypeBuilder setFixedTime(int fixedTime) {
        this.fixedTime = fixedTime;
        return this;
    }

    public DimensionTypeBuilder setLogicalHeight(float logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

    public DimensionTypeBuilder setEffects(Effect effects) {
        this.effects = effects;
        return this;
    }

    public DimensionTypeBuilder setInfiniburn(String infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    public DimensionTypeBuilder setMinY(double minY) {
        this.minY = minY;
        return this;
    }

    public DimensionTypeBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    public DimensionTypeBuilder setMonsterSpawnLightLevel(Map<String, Map<Integer, Integer>> monsterSpawnLightLevel) {
        this.monsterSpawnLightLevel = monsterSpawnLightLevel;
        return this;
    }

    public DimensionTypeBuilder setMonsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        return this;
    }

    @NotNull
    private JsonObject getJsonObject() {
        JsonObject json = new JsonObject();

        json.addProperty("ultrawarm", this.isUltrawarm);
        json.addProperty("natural", this.isNatural);
        json.addProperty("piglin_safe", this.isPiglinSafe);
        json.addProperty("respawn_anchor_works", this.respawnAnchorWorks);
        json.addProperty("bed_works", this.bedWorks);
        json.addProperty("has_raids", this.hasRaids);
        json.addProperty("has_skylight", this.hasSkylight);
        json.addProperty("has_ceiling", this.hasCeiling);
        json.addProperty("coordinate_scale", this.coordinateScale);
        json.addProperty("ambient_light", this.ambientLight);
        json.addProperty("fixed_time", this.fixedTime);
        json.addProperty("logical_height", this.logicalHeight);
        json.addProperty("effects", this.effects.getId());
        json.addProperty("infiniburn", this.infiniburn);
        json.addProperty("min_y", this.minY);
        json.addProperty("height", this.height);
        json.addProperty("monster_spawn_block_light_limit", this.monsterSpawnBlockLightLimit);
        return json;
    }

    @NotNull
    private static JsonObject getJsonObject(Map.Entry<String, Map<Integer, Integer>> entry) {
        JsonObject monsterSpawnLightLevelEntryJson = new JsonObject();
        monsterSpawnLightLevelEntryJson.addProperty("type", entry.getKey());

        JsonObject monsterSpawnLightLevelEntryValueJson = new JsonObject();
        for (Map.Entry<Integer, Integer> valueEntry : entry.getValue().entrySet()) {
            monsterSpawnLightLevelEntryValueJson.addProperty(
                    valueEntry.getKey() == 0 ? "min_inclusive" : "max_inclusive", valueEntry.getValue());
        }

        monsterSpawnLightLevelEntryJson.add("value", monsterSpawnLightLevelEntryValueJson);
        return monsterSpawnLightLevelEntryJson;
    }
}
