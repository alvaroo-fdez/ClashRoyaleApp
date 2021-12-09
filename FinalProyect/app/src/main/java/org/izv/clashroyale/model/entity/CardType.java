package org.izv.clashroyale.model.entity;

import androidx.room.Embedded;

public class CardType {

    @Embedded
    public Card card;

    @Embedded(prefix="type_")
    public Type type;
}
