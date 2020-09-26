package ru.otus.hw17.messagesystem.message;

import org.junit.jupiter.api.Test;
import ru.otus.hw17.messagesystem.message.Serializers;

import static org.assertj.core.api.Assertions.assertThat;

class SerializersTest {

    @Test
    void serializeDeSerialize() {
        TestData testData = new TestData(1, "str", 2);

        byte[] data = Serializers.serialize(testData);

        TestData object = (TestData) Serializers.deserialize(data);
        assertThat(object).isEqualTo(testData);
    }
}
