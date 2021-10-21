package alexthw.hexblades.commands.args;

import java.util.Arrays;
import java.util.Collection;

public class KnowledgeArgumentType extends HexArgumentType {

    @Override
    public Collection<String> getExamples() {
        return Arrays.asList("sacred", "wicked", "blood", "soul", "mind", "energy");
    }

}
