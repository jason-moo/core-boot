package com.xzn.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * {@link RedisSerializer} that can read and write JSON using <a href="https://github.com/alibaba/fastjson/">Fastjson</a>.
 * <p>
 * This converter can be used to bind to typed beans, or untyped {@link java.util.HashMap HashMap} instances.
 * <b>Note:</b>Null objects are serialized as empty arrays and vice versa.
 * <p>
 * Created by lll@xkeshi.com on 17/4/14.
 */
public class RedisValueSerializer implements RedisSerializer<Object> {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private static SerializerFeature[] features = {SerializerFeature.WriteClassName};

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return JSON.parseObject(bytes, Object.class);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        try {
            return JSON.toJSONBytes(object, features);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    private static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

}
