package Input;

import java.util.Arrays;
import java.util.List;

public class Input {
    public List<String> inputBitSorozatok;

    public static Input feladatsor;

    static {
        feladatsor = new Input();
        feladatsor.inputBitSorozatok = Arrays.asList("00111011", "00000000", "10101101");
    }
}
