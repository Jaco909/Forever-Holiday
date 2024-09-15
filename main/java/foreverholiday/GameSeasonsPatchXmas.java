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

        if ((halloween || aprilFools || newYear) && !ForeverHoliday.settings.overrideSeason) {
            //System.out.println("Xmas blocked by vanilla season time!");
            christmas = false;
        }else if (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason != 2) {
            //System.out.println("Xmas blocked for another forced!");
            christmas = false;
        } else if (!ForeverHoliday.settings.overrideSeason && christmass) {
            //vanilla season time
            christmas = true;
        } else if ((ForeverHoliday.settings.selectedSeason == 2 && !halloween && !aprilFools && !newYear) || (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason == 2)) {
            //System.out.println("Xmas forced!");
            christmas = true;
        }
        return christmas;
    }
}
