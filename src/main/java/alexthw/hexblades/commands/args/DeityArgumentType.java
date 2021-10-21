package alexthw.hexblades.commands.args;

import java.util.Collection;

import static alexthw.hexblades.deity.HexDeities.DeityNames;

public class DeityArgumentType extends HexArgumentType {

    @Override
    public Collection<String> getExamples() {
        return DeityNames;
    }

}

