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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.util.Effect;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

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
    private double ambientLight = 0.0;
    private Integer fixedTime = null;
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

    /**
     * Sets the 'natural' flag of the DimensionTypeBuilder.
     *
     * @param natural if the dimension is natural and not artificially created
     * @return the DimensionTypeBuilder instance
     */
    public DimensionTypeBuilder setNatural(boolean natural) {
        isNatural = natural;
        return this;
    }

    /**
     * Sets whether the dimension is piglin safe.
     *
     * @param piglinSafe true if the dimension is piglin safe, false otherwise
     * @return the DimensionTypeBuilder instance
     */
    public DimensionTypeBuilder setPiglinSafe(boolean piglinSafe) {
        isPiglinSafe = piglinSafe;
        return this;
    }

    /**
     * Sets whether the respawn anchor works in the dimension type.
     *
     * @param respawnAnchorWorks whether the respawn anchor works
     * @return the DimensionTypeBuilder object
     */
    public DimensionTypeBuilder setRespawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }

    /**
     * Sets whether the bed functionality works in the dimension being built.
     *
     * @param bedWorks true if bed functionality works, false otherwise
     * @return the instance of DimensionTypeBuilder with the bed functionality set
     */
    public DimensionTypeBuilder setBedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    /**
     * Sets whether the dimension type has raids.
     *
     * @param hasRaids true if the dimension type has raids, false otherwise
     * @return the DimensionTypeBuilder instance
     */
    public DimensionTypeBuilder setHasRaids(boolean hasRaids) {
        this.hasRaids = hasRaids;
        return this;
    }

    /**
     * Sets whether the dimension type has a skylight.
     *
     * @param hasSkylight true if the dimension type has a skylight, false otherwise
     * @return the DimensionTypeBuilder instance
     */
    public DimensionTypeBuilder setHasSkylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    /**
     * Sets whether the dimension has a ceiling or not.
     *
     * @param hasCeiling true if the dimension has a ceiling, false otherwise
     * @return the DimensionTypeBuilder object
     */
    public DimensionTypeBuilder setHasCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    /**
     * Sets the coordinate scale for the DimensionTypeBuilder.
     *
     * @param coordinateScale the desired coordinate scale for the DimensionTypeBuilder
     * @return the updated DimensionTypeBuilder object
     */
    public DimensionTypeBuilder setCoordinateScale(int coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    /**
     * Sets the ambient light for the DimensionTypeBuilder.
     *
     * @param ambientLight the desired ambient light value, must be between 0.0 and 1.0
     * @return the updated DimensionTypeBuilder object
     * @throws IllegalArgumentException if the ambient light value is not within the valid range
     */
    public DimensionTypeBuilder setAmbientLight(@Range(from = 0L, to = 1L) double ambientLight) {

        if (ambientLight < 0.0 || ambientLight > 1.0) {
            throw new IllegalArgumentException("Ambient light must be between 0.0 and 1.0");
        }

        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the fixed time for the dimension.
     *
     * @param fixedTime the fixed time value to set for the dimension
     * @return the DimensionTypeBuilder object
     */
    public DimensionTypeBuilder setFixedTime(Integer fixedTime) {
        this.fixedTime = fixedTime;
        return this;
    }

    /**
     *
     */
    public DimensionTypeBuilder setLogicalHeight(float logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

    /**
     * Sets the effects for the DimensionTypeBuilder.
     *
     * @param effects the effects to be set
     * @return the DimensionTypeBuilder object with the effects set
     */
    public DimensionTypeBuilder setEffects(Effect effects) {
        this.effects = effects;
        return this;
    }

    /**
     * Sets the infiniburn for the DimensionTypeBuilder.
     *
     * @param infiniburn the infiniburn to be set
     * @return the DimensionTypeBuilder object with the infiniburn set
     */
    public DimensionTypeBuilder setInfiniburn(String infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    /**
     * Sets the minimum Y coordinate for the DimensionTypeBuilder.
     *
     * @param minY the minimum Y coordinate to be set
     * @return the DimensionTypeBuilder object with the minimum Y coordinate set
     */
    public DimensionTypeBuilder setMinY(double minY) {
        this.minY = minY;
        return this;
    }

    /**
     * Sets the height of the DimensionTypeBuilder object.
     *
     * @param height the height value to be set
     * @return the updated DimensionTypeBuilder object
     */
    public DimensionTypeBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    /**
     * Sets the monster spawn light level for the dimension.
     *
     * @param monsterSpawnLightLevel The map containing the monster spawn light level.
     *                               The keys are strings representing the biome names.
     *                               The values are maps containing pairs of minimum and maximum light levels for each biome.
     * @return The DimensionTypeBuilder instance to enable method chaining.
     */
    public DimensionTypeBuilder setMonsterSpawnLightLevel(Map<String, Map<Integer, Integer>> monsterSpawnLightLevel) {
        this.monsterSpawnLightLevel = monsterSpawnLightLevel;
        return this;
    }

    /**
     *
     */
    public DimensionTypeBuilder setMonsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        return this;
    }

    /**
     * Converts the DimensionTypeBuilder object to a JsonElement.
     *
     * @return The converted JsonElement.
     */
    @NotNull
    public JsonElement toJson() {
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

        if (this.fixedTime != null) {
            json.addProperty("fixed_time", this.fixedTime);
        }

        json.addProperty("logical_height", this.logicalHeight);
        json.addProperty("effects", this.effects.getId());
        json.addProperty("infiniburn", this.infiniburn);
        json.addProperty("min_y", this.minY);
        json.addProperty("height", this.height);
        json.addProperty("monster_spawn_block_light_limit", this.monsterSpawnBlockLightLimit);

        val monsterSpawnLevel = new JsonObject();

        for (Map.Entry<String, Map<Integer, Integer>> entry : this.monsterSpawnLightLevel.entrySet()) {
            monsterSpawnLevel.add(entry.getKey(), getJsonObject(entry));
        }

        json.add("monster_spawn_light_level", monsterSpawnLevel);

        return json;
    }

    /**
     * Returns a JsonObject representation of the object.
     *
     * @return The JsonObject representation of the object.
     */
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
