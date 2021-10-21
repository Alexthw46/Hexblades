package alexthw.hexblades.commands.args;

import alexthw.hexblades.commands.HexCommands;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public class HexArgumentType implements ArgumentType<String> {

    @Override
    public String toString() {
        return "string()";
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String read = reader.readUnquotedString();
        if (getExamples().contains(read)) {
            return read;
        }
        throw HexCommands.INCORRECT_RESULT.createWithContext(reader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (String suggestion : getExamples()) {
            builder = builder.suggest(suggestion);
        }
        return builder.buildFuture();
    }

}
