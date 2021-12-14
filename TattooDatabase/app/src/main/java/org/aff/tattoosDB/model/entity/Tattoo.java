package org.aff.tattoosDB.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "tattoos",
        indices = {@Index(value = "color", unique = true)},
        foreignKeys = {@ForeignKey(entity = Type.class, parentColumns = "id", childColumns = "idtype", onDelete = ForeignKey.CASCADE)})

public class Tattoo implements Parcelable {

    public Tattoo() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "ubicacion")
    public String ubicacion;

    @ColumnInfo(name = "duracion")
    public int duracion;

    @ColumnInfo(name = "horas")
    public int horas;

    @ColumnInfo(name = "idtype")
    public int idtype;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "date")
    public String date;


    protected Tattoo(Parcel in) {
        id = in.readInt();
        color = in.readString();
        ubicacion = in.readString();
        duracion = in.readInt();
        horas = in.readInt();
        idtype = in.readInt();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(color);
        dest.writeString(ubicacion);
        dest.writeInt(duracion);
        dest.writeInt(horas);
        dest.writeInt(idtype);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tattoo> CREATOR = new Creator<Tattoo>() {
        @Override
        public Tattoo createFromParcel(Parcel in) {
            return new Tattoo(in);
        }

        @Override
        public Tattoo[] newArray(int size) {
            return new Tattoo[size];
        }
    };
}
