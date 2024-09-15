package foreverholiday;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.seasons.GameSeasons;
import net.bytebuddy.asm.Advice;

import static necesse.engine.seasons.GameSeasons.isBetween;

@ModMethodPatch(target = GameSeasons.class, name = "isHalloween", arguments = {})
public class GameSeasonsPatchHalloween {
    @Advice.OnMethodEnter(
            skipOn = Advice.OnNonDefaultValue.class
    )
    static boolean onEnter() {
        return true;
    }
    @Advice.OnMethodExit()
    static boolean onExit(@Advice.Return(readOnly = false) boolean halloween) {

        //season data is private, and calling the function is recursive, so we check the dates again manually to avoid crashes
        boolean aprilFools = isBetween(1, 4, 2, 4);
        boolean halloweens = isBetween(18, 10, 7, 11);
        boolean christmas = isBetween(1, 12, 28, 12);
        boolean newYear = isBetween(28, 12, 7, 1);
        boolean forced = ForeverHoliday.settings.overrideSeason;

        if ((christmas || aprilFools || newYear)  && !ForeverHoliday.settings.overrideSeason) {
            //System.out.println("Halloween blocked by vanilla season time!");
            halloween = false;
        }else if (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason != 1) {
            //System.out.println("Halloween blocked for another forced!");
            halloween = false;
        } else if (halloweens && !ForeverHoliday.settings.overrideSeason) {
            //vanilla season time
            halloween = true;
        } else if ((ForeverHoliday.settings.selectedSeason == 1 && !christmas && !aprilFools && !newYear) || (ForeverHoliday.settings.overrideSeason && ForeverHoliday.settings.selectedSeason == 1)) {
            //System.out.println("Halloween forced!");
            halloween = true;
        }
        return halloween;
    }
}
