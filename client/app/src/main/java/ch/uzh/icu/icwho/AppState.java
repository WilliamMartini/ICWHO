package ch.uzh.icu.icwho;

import ch.uzh.icu.icwho.models.Event;

/**
 * Created by Flo on 10.08.2016.
 */

public class AppState {
    // state of the app; needed for the back button
    public static int currentState = 0;

    // slot for event which is passed for the detailed view
    public static Event currentEvent;
}
