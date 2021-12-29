package alexthw.hexblades.mixin;

import elucent.eidolon.codex.Category;
import elucent.eidolon.codex.CodexChapters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(CodexChapters.class)
public interface CodexChaptersMixin {

    @Accessor List<Category> getCategories();
}
