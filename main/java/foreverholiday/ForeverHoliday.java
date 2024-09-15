package foreverholiday;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class ForeverHoliday {
    public static Settings settings;

    public void init() {
        System.out.println("Forever Holiday Loaded!");
    }
    public ModSettings initSettings() {
        settings = new Settings();
        return settings;
    }
}
