package ru.otus.hw17.messagesystem.message;

import java.io.*;

class Serializers {
    private Serializers() {
    }

    static byte[] serialize(Object data) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(baos)) {
            os.writeObject(data);
            os.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new SerializerError("Serialization error", e);
        }
    }

    static Object deserialize(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream is = new ObjectInputStream(bis)) {
            return is.readObject();
        } catch (Exception e) {
            throw new SerializerError("DeSerialization error", e);
        }
    }
}
