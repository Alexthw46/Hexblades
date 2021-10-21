package alexthw.hexblades.commands.args;

import alexthw.hexblades.deity.HexFacts;

import java.util.Collection;

public class FactArgumentType extends HexArgumentType {

    @Override
    public Collection<String> getExamples() {
        return HexFacts.Facts;
    }

}
