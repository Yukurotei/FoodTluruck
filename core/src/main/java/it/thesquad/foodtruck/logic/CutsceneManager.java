package it.thesquad.foodtruck.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CutsceneManager {
    private final ConcurrentLinkedQueue<CutsceneEvent> events = new ConcurrentLinkedQueue<>();

    public void addEvent(CutsceneEvent event) {
        events.add(event);
    }

    public void update(float timePassed) {
        for (CutsceneEvent event : events) {
            event.trigger(timePassed);
        }
    }
}
