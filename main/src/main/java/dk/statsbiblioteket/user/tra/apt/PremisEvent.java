package dk.statsbiblioteket.user.tra.apt;

import java.time.Instant;

/**
 *
 */
public class PremisEvent implements Event {

    protected String eventId;
    protected String eventType;
    protected String details;
    protected Instant instant;  // Originally java.util.Date.
    protected boolean success;

    public PremisEvent(String eventId, String eventType, String details, Instant instant, boolean success) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.details = details;
        this.instant = instant;
        this.success = success;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDetails() {
        return details;
    }

    public Instant getInstant() {
        return instant;
    }

    public boolean isSuccess() {
        return success;
    }
}
