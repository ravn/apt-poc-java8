package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Item;

/**
 *
 */
public class StringItem implements Item {
    private String s;

    public StringItem(String s) {
        this.s = s;
    }
}
