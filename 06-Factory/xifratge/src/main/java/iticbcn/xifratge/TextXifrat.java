package iticbcn.xifratge;

public class TextXifrat {
    byte[] array;

    public TextXifrat(byte[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        // bytes to string
        return new String(array);
    }

    public byte[] getBytes(){
        // getter
        return array;
    }
}
