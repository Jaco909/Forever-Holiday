package foreverholiday;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.seasons.GameSeasons;
import net.bytebuddy.asm.Advice;

import java.util.Calendar;
import java.util.Locale;

import static necesse.engine.seasons.GameSeasons.isBetween;

@ModMethodPatch(target = GameSeasons.class, name = "isNewYear", arguments = {})
public class GameSeasonsPatchNewYears {
    @Advice.OnMethodEnter(
            skipOn = Advice.OnNonDefaultValue.class
    )
    static boolean onEnter() {
        return true;
    }
    @Advice.OnMethodExit()
    static boolean onExit(@Advice.Return(readOnly = false) boolean newYear) {

        //season data is private, and calling the function is recursive, so we check the dates again manually to avoid crashes
        boolean aprilFools = isBetween(1, 4, 2, 4);
        boolean halloween = isBetween(18, 10, 7, 11);
        boolean christmas = isBetween(1, 12, 28, 12);
        boolean newYears = isBetween(28, 12, 7, 1);
        boolean forced = ForeverHoliday.settingsGetter.getBoolean("override");
        boolean selected = ForeverHoliday.settingsGetter.getSelection("selectHoliday").toString().equalsIgnoreCase("years");

        if ((halloween || aprilFools || christmas)  && !forced) {
            //blocked by vanilla season time
            newYear = false;
        } else if (forced && !selected) {
            //blocked for another forced
            newYear = false;
        } else if (!forced && newYears) {
            //vanilla season time
            newYear = true;
        } else if ((selected && !halloween && !aprilFools && !christmas) || (forced && selected)) {
            //forced
            newYear = true;
        }
        return newYear;
    }
}
