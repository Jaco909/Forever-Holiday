package foreverholiday;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.seasons.GameSeasons;
import net.bytebuddy.asm.Advice;

import static necesse.engine.seasons.GameSeasons.isBetween;

@ModMethodPatch(target = GameSeasons.class, name = "isAprilFools", arguments = {})
public class GameSeasonsPatchAprilFools {
    @Advice.OnMethodEnter(
            skipOn = Advice.OnNonDefaultValue.class
    )
    static boolean onEnter() {
        return true;
    }
    @Advice.OnMethodExit()
    static boolean onExit(@Advice.Return(readOnly = false) boolean aprilFools) {

        //season data is private, and calling the function is recursive, so we check the dates again manually to avoid crashes
        boolean aprilFoolss = isBetween(1, 4, 2, 4);
        boolean halloween = isBetween(18, 10, 7, 11);
        boolean christmas = isBetween(1, 12, 28, 12);
        boolean newYear = isBetween(28, 12, 7, 1);
        boolean forced = ForeverHoliday.settingsGetter.getBoolean("override");
        boolean selected = ForeverHoliday.settingsGetter.getSelection("selectHoliday").toString().equalsIgnoreCase("fools");

        if ((christmas || halloween || newYear)  && !forced) {
            //blocked by vanilla season time
            aprilFools = false;
        }else if (forced && !selected) {
            //blocked for another forced
            aprilFools = false;
        } else if (!forced && aprilFoolss) {
            //vanilla season time
            aprilFools = true;
        } else if ((selected && !christmas && !halloween && !newYear) || (forced && selected)) {
            //forced
            aprilFools = true;
        }
        return aprilFools;
    }
}
