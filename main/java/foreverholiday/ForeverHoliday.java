package foreverholiday;

import customsettingsui.components.settings.SelectionSetting;
import customsettingsui.settings.CustomModSettings;
import customsettingsui.settings.CustomModSettingsGetter;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class ForeverHoliday {
    public static CustomModSettingsGetter settingsGetter;

    public void init() {
        System.out.println("Forever Holiday Loaded!");
    }
    public ModSettings initSettings() {
        CustomModSettings customModSettings = new CustomModSettings()
                .addTextSeparator("selectHoliday")
                .addSelectionSetting("selectHoliday", 0,
                        new SelectionSetting.Option("none","none"),
                        new SelectionSetting.Option("xmas","xmas"),
                        new SelectionSetting.Option("spooky","spooky"),
                        new SelectionSetting.Option("years","years"),
                        new SelectionSetting.Option("fools","fools")
                )
                .addBooleanSetting("override",false)
                .addBooleanSetting("restart",true);
        settingsGetter = customModSettings.getGetter();
        return customModSettings;
    }
}
