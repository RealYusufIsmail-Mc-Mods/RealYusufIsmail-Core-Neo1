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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SurfaceRule {
    private String type;
    private Sequence[] sequence;

    public SurfaceRule setType(String type) {
        this.type = type;
        return this;
    }

    public SurfaceRule setSequence(Sequence[] sequence) {
        this.sequence = sequence;
        return this;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", type);
        JsonArray sequenceArray = new JsonArray();
        for (Sequence seq : sequence) {
            sequenceArray.add(seq.toJson());
        }
        object.add("sequence", sequenceArray);
        return object;
    }

    public static class Sequence {
        private String type;
        private ResultState resultState;

        public Sequence setType(String type) {
            this.type = type;
            return this;
        }

        public Sequence setResultState(ResultState resultState) {
            this.resultState = resultState;
            return this;
        }

        public JsonElement toJson() {
            JsonObject object = new JsonObject();
            object.addProperty("type", type);
            object.add("result_state", resultState.toJson());
            return object;
        }
    }

    public static class ResultState {
        private String name;

        public ResultState setName(String name) {
            this.name = name;
            return this;
        }

        public JsonElement toJson() {
            JsonObject object = new JsonObject();
            object.addProperty("Name", name);
            return object;
        }
    }
}
