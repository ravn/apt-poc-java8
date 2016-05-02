package dk.statsbiblioteket.user.tra.model;

public interface Query<Q, V> {
    V query(Q query);
}
