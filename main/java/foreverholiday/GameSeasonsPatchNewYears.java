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
        /*Calendar instance = Calendar.getInstance(Locale.ENGLISH);
        int month = instance.get(2) + 1;
        int day = instance.get(5);*/
        boolean aprilFools = isBetween(1, 4, 2, 4);
        boolean halloween = isBetween(18, 10, 7, 11);
        boolean christmas = isBetween(1, 12, 28, 12);
        boolean newYears = isBetween(28, 12, 7, 1);

        if ((halloween || aprilFools || christmas)  && !ForeverHoliday.settings.overrideSeason) {
            //System.out.println("New Years blocked by vanilla season time!");
            newYear = false;
        } else if (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason != 3) {
            //System.out.println("New Years blocked for another forced!");
            newYear = false;
        } else if (!ForeverHoliday.settings.overrideSeason && newYears) {
            //vanilla season time
            newYear = true;
        } else if ((ForeverHoliday.settings.selectedSeason == 3 && !halloween && !aprilFools && !christmas) || (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason == 3)) {
            //System.out.println("New Years forced!");
            newYear = true;
        }
        return newYear;
    }
}
