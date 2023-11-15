package io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.generator.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;

public class LayerBuilder {
    private final Block block;
    private final int height;

    public LayerBuilder(Block block, int height) {
        this.block = block;
        this.height = height;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("block", block.getName().getString());
        object.addProperty("height", height);

        return object;
    }
}
