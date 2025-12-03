package it.thesquad.foodtruck.logic;

import java.util.ArrayList;
import java.util.List;

public class CutsceneManager {
    private List<CutsceneEvent> events = new ArrayList<>();

    public void addEvent(CutsceneEvent event) {
        events.add(event);
    }

    public void update(float timePassed) {
        for (CutsceneEvent event : events) {
            event.trigger(timePassed);
        }
    }
}
