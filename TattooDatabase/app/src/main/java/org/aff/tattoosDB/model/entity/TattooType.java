package org.aff.tattoosDB.model.entity;

import androidx.room.Embedded;

public class TattooType {

    @Embedded
    public Tattoo tattoo;

    @Embedded(prefix = "type_")
    public Type type;
}
