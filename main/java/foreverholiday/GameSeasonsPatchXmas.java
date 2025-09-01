package foreverholiday;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.seasons.GameSeasons;
import net.bytebuddy.asm.Advice;

import static necesse.engine.seasons.GameSeasons.isBetween;

@ModMethodPatch(target = GameSeasons.class, name = "isChristmas", arguments = {})
public class GameSeasonsPatchXmas {
    @Advice.OnMethodEnter(
            skipOn = Advice.OnNonDefaultValue.class
    )
    static boolean onEnter() {
        return true;
    }
    @Advice.OnMethodExit()
    static boolean onExit(@Advice.Return(readOnly = false) boolean christmas) {

        //season data is private, and calling the function is recursive, so we check the dates again manually to avoid crashes
        boolean aprilFools = isBetween(1, 4, 2, 4);
        boolean halloween = isBetween(18, 10, 7, 11);
        boolean christmass = isBetween(1, 12, 28, 12);
        boolean newYear = isBetween(28, 12, 7, 1);
        boolean forced = ForeverHoliday.settingsGetter.getBoolean("override");
        boolean selected = ForeverHoliday.settingsGetter.getSelection("selectHoliday").toString().equalsIgnoreCase("xmas");

        if ((halloween || aprilFools || newYear) && !forced) {
            //blocked by vanilla season time
            christmas = false;
        } else if (forced && !selected) {
            //blocked for another forced
            christmas = false;
        } else if (!forced && christmass) {
            //vanilla season time
            christmas = true;
        } else if ((selected && !halloween && !aprilFools && !newYear) || (forced && selected)) {
            //forced
            christmas = true;
        }
        return christmas;
    }
}
