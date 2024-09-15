package foreverholiday;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

public class Settings extends ModSettings {
    //0 = none
    //1 = Halloween
    //2 = Xmas
    //3 = New Years
    //4 = April Fools
    public int selectedSeason = 0;
    public boolean overrideSeason = false;

    @Override
    public void addSaveData(SaveData save) {
        save.addInt("selectedseason", selectedSeason,"Set the current holiday to be forced.\n                            //0 = None\n                            //1 = Halloween\n                            //2 = Christmas\n                            //3 = New Years\n                            //4 = April Fools");
        save.addBoolean("overrideseason", overrideSeason,"Override the vanilla season calendar with the forced holiday.\n                              //false = Vanilla seasons will override the forced season.\n                              //true = The forced season will always be active, regardless of calendar date or vanilla season timing.");
    }

    @Override
    public void applyLoadData(LoadData save) {
        if (save == null)
            return;
        selectedSeason = save.getInt("selectedseason");
        overrideSeason = save.getBoolean("overrideseason");
    }


}