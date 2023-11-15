package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Setter;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Setter
public class DimensionBuilder {
    private final String modid;
    private final ExistingFileHelper existingFileHelper;

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
    private String effects = "minecraft:overworld";
    private String infiniburn = "minecraft:infiniburn_overworld";
    private double minY = 0;
    private double height = 0;
    private Map<String, Map<Integer, Integer>> monsterSpawnLightLevel = new HashMap<>();
    private int monsterSpawnBlockLightLimit = 0;

    protected DimensionBuilder(String modid, ExistingFileHelper existingFileHelper) {
        this.modid = modid;
        this.existingFileHelper = existingFileHelper;
    }

    public JsonElement toJson() {
        JsonObject json = getJsonObject();

        JsonObject monsterSpawnLightLevelJson = new JsonObject();
        for (Map.Entry<String, Map<Integer, Integer>> entry : this.monsterSpawnLightLevel
            .entrySet()) {
            JsonObject monsterSpawnLightLevelEntryJson = getJsonObject(entry);
            monsterSpawnLightLevelJson.add(entry.getKey(), monsterSpawnLightLevelEntryJson);
        }
        json.add("monster_spawn_light_level", monsterSpawnLightLevelJson);

        return json;
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
        json.addProperty("effects", this.effects);
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
                    valueEntry.getKey() == 0 ? "min_inclusive" : "max_inclusive",
                    valueEntry.getValue());
        }

        monsterSpawnLightLevelEntryJson.add("value", monsterSpawnLightLevelEntryValueJson);
        return monsterSpawnLightLevelEntryJson;
    }
}
