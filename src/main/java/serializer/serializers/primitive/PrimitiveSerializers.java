package serializer.serializers.primitive;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class PrimitiveSerializers {
    

    public static void serializeBoolean(boolean val, ByteBuf out) throws IOException
    {
        out.writeBoolean(val);
    }

    public static boolean deserializeBoolean(ByteBuf in) throws IOException
    {
        return in.readBoolean();
    }

    public static void serializeByte(byte val, ByteBuf out) throws IOException
    {
        out.writeByte(val);
    }

    public static byte deserializeByte(ByteBuf in) throws IOException
    {
        return in.readByte();
    }

    public static void serializeChar(char val, ByteBuf out) throws IOException
    {
        out.writeChar(val);
    }

    public static int deserializeChar(ByteBuf in) throws IOException
    {
        return in.readChar();
    }

    public static void serializeShort(short val, ByteBuf out) throws IOException
    {
        out.writeShort(val);
    }

    public static short deserializeShort(ByteBuf in) throws IOException
    {
        return in.readShort();
    }

    public static void serializeInt(int val, ByteBuf out) throws IOException
    {
        out.writeInt(val);
    }

    public static int deserializeInt(ByteBuf in) throws IOException
    {
        return in.readInt();
    }

    public static void serializeLong(long val, ByteBuf out) throws IOException
    {
        out.writeLong(val);
    }

    public static long deserializeLong(ByteBuf in) throws IOException
    {
        return in.readLong();
    }

    public static void serializeFloat(float val, ByteBuf out) throws IOException
    {
        out.writeFloat(val);
    }

    public static float deserializeFloat(ByteBuf in) throws IOException
    {
        return in.readFloat();
    }

    public static void serializeDouble(double val, ByteBuf out) throws IOException
    {
        out.writeDouble(val);
    }

    public static double deserializeDouble(ByteBuf in) throws IOException
    {
        return in.readDouble();
    }

}
