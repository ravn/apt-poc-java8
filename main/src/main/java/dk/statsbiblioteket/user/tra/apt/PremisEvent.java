package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Id;
import dk.statsbiblioteket.user.tra.model.Item;

import java.time.Instant;
import java.util.Objects;

/**
 *
 */
public class PremisEvent<T extends Item> implements Event, Id {

    protected T belongsTo;
    protected String id;
    protected String eventType;
    protected String details;
    protected Instant instant;  // Originally java.util.Date.
    protected boolean success;

    public PremisEvent(T belongsTo, String id, String eventType, String details, Instant instant, boolean success) {
        this.belongsTo = Objects.requireNonNull(belongsTo);
        this.id = Objects.requireNonNull(id);
        this.eventType = Objects.requireNonNull(eventType);
        this.details = Objects.requireNonNull(details);
        this.instant = Objects.requireNonNull(instant);
        this.success = success;
    }

    public T getBelongsTo() {
        return belongsTo;
    }

    public String id() {
        return id;
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
