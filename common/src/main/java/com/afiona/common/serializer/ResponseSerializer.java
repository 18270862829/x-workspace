package com.afiona.common.serializer;

import com.afiona.common.pojo.JsonResult;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.pagehelper.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Shang fei on 2020/1/8 15:32
 */
public class ResponseSerializer extends JsonSerializer<JsonResult> {
    public ResponseSerializer() {
    }

    public void serialize(JsonResult value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("code", value.getCode());
        gen.writeStringField("msg", value.getMsg());
        Object data = value.getData();
        if (data instanceof Page) {
            gen.writeFieldName("data");
            gen.writeObjectField("pageNum", ((Page) data).getPageNum());
            gen.writeObjectField("totalCount", ((Page) data).getTotal());
            gen.writeObjectField("pageSize", ((Page) data).getPageSize());
            gen.writeObjectField("totalPage", ((Page) data).getPages());
            serializerProvider.defaultSerializeValue(new ArrayList((Collection) data), gen);
        } else if (data != null) {
            gen.writeFieldName("data");
            serializerProvider.defaultSerializeValue(data, gen);
        }

        gen.writeEndObject();
    }
}
