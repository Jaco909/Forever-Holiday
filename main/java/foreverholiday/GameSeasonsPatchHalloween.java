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
        boolean forced = ForeverHoliday.settingsGetter.getBoolean("override");
        boolean selected = ForeverHoliday.settingsGetter.getSelection("selectHoliday").toString().equalsIgnoreCase("spooky");

        if ((christmas || aprilFools || newYear)  && !forced) {
            //blocked by vanilla season time
            halloween = false;
        }else if (forced && !selected) {
            //blocked for another forced
            halloween = false;
        } else if (halloweens && !forced) {
            //vanilla season time
            halloween = true;
        } else if ((selected && !christmas && !aprilFools && !newYear) || (forced && selected)) {
            //forced
            halloween = true;
        }
        return halloween;
    }
}
