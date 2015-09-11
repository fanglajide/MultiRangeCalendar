package supercalendar;

import android.app.Application;

import me.drakeet.library.CrashWoodpecker;

/**
 * Created by chanlevel on 15/9/11.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashWoodpecker.fly().to(this);
    }
}
