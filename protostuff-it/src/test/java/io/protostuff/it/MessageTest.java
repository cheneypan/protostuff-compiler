package io.protostuff.it;

import io.protostuff.it.message_test.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public static final SimpleMessage A = SimpleMessage.newBuilder()
            .setInt32(42)
            .setString("abra")
            .build();
    public static final SimpleMessage A_COPY = SimpleMessage.newBuilder()
            .setInt32(42)
            .setString("abra")
            .build();
    public static final SimpleMessage B = SimpleMessage.newBuilder()
            .setInt32(43)
            .setString("cadabra")
            .build();
    public static final TestMap TEST_MAP = TestMap.newBuilder()
            .putMapStringString("key", "value")
            .putMapStringString("test", "test")
            .build();

    @Test
    public void createdInstance() throws Exception {
        ParentMsg instance = ParentMsg.newBuilder()
                .setNestedMsg(NestedMsg.newBuilder()
                        .setName("1")
                        .build())
                .addNestedRepeatedMsg(NestedMsg.newBuilder()
                        .setName("2")
                        .build())
                .build();
        assertTrue(instance.hasNestedMsg());
        assertEquals("1", instance.getNestedMsg().getName());
        assertEquals(1, instance.getNestedRepeatedMsgCount());
        assertEquals("2", instance.getNestedRepeatedMsg(0).getName());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(A, A_COPY);
        assertNotEquals(A, B);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(A.hashCode(), A_COPY.hashCode());
        assertNotEquals(A.hashCode(), B.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("SimpleMessage{int32=42, string=abra}", A.toString());
    }

    @Test
    public void testToString_integer_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setInt32(15)
                .build();
        assertEquals("SimpleMessage{int32=15}", message.toString());
    }

    @Test
    public void testToString_string_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setString("test")
                .build();
        assertEquals("SimpleMessage{string=test}", message.toString());
    }

    @Test
    public void testToString_message_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .setMessage(TestMessage.newBuilder()
                        .setA(123)
                        .build())
                .build();
        assertEquals("SimpleMessage{message=TestMessage{a=123}}", message.toString());
    }

    @Test
    public void testToString_repeated_string_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .addRepeatedString("test1")
                .addRepeatedString("test2")
                .build();
        assertEquals("SimpleMessage{repeated_string=[test1, test2]}", message.toString());
    }

    @Test
    public void testToString_repeated_int32_field() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder()
                .addRepeatedInt32(41)
                .addRepeatedInt32(42)
                .build();
        assertEquals("SimpleMessage{repeated_int32=[41, 42]}", message.toString());
    }

    @Test
    public void testToString_MessageWithoutFields() throws Exception {
        MessageWithoutFields message = MessageWithoutFields.newBuilder().build();
        assertEquals("MessageWithoutFields{}", message.toString());
    }

    @Test
    public void testModifyConstructedMessage_normal_setter() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder().build();
        thrown.expect(IllegalStateException.class);
        message.setInt32(1);
    }

    @Test
    public void testModifyConstructedMessage_repeated_setter() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder().build();
        thrown.expect(IllegalStateException.class);
        message.setRepeatedInt32List(Collections.emptyList());
    }

    @Test
    public void testModifyConstructedMessage_adder_single() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder().build();
        thrown.expect(IllegalStateException.class);
        message.addRepeatedInt32(1);
    }

    @Test
    public void testModifyConstructedMessage_adder_list() throws Exception {
        SimpleMessage message = SimpleMessage.newBuilder().build();
        thrown.expect(IllegalStateException.class);
        message.addRepeatedInt32(1);
    }

    @Test
    public void testMap_getter_map() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("key", "value");
        expected.put("test", "test");
        Assert.assertEquals(expected, TEST_MAP.getMapStringStringMap());
    }

    @Test
    public void testMap_getter_single() throws Exception {
        Assert.assertEquals("value", TEST_MAP.getMapStringString("key"));
    }

    @Test
    public void testMap_getter_count() throws Exception {
        Assert.assertEquals(2, TEST_MAP.getMapStringStringCount());
    }

    @Test
    public void testMap_setter_map() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("test", "test");
        TestMap instance = TestMap.newBuilder()
                .setMapStringStringMap(map)
                .build();
        Assert.assertEquals(map, instance.getMapStringStringMap());
    }

    @Test
    public void testModifyConstructedMap_setter_map() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("test", "test");
        thrown.expect(IllegalStateException.class);
        TEST_MAP.setMapStringStringMap(map);
    }

    @Test
    public void testMap_setter_single() throws Exception {
        thrown.expect(IllegalStateException.class);
        TEST_MAP.putMapStringString("a", "b");
    }
}
