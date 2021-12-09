package org.izv.clashroyale.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "card",
        indices = {@Index(value = "name", unique = true)},
        foreignKeys = {@ForeignKey(entity = Type.class, parentColumns = "id", childColumns = "idtype", onDelete = ForeignKey.CASCADE)})
public class Card implements Parcelable {

    //id autonumérico
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idtype")
    public long idtype;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "elixir")
    public double elixir;

    @ColumnInfo(name = "url")
    public String url;

    public Card() {
    }

    protected Card(Parcel in) {
        id = in.readLong();
        name = in.readString();
        idtype = in.readLong();
        level = in.readInt();
        elixir = in.readDouble();
        url = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public boolean isValid() {
        return !(name.isEmpty() || elixir <= 0  || level <= 0 || url.isEmpty() || idtype <= 0);
        //shortcut
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idtype);
        dest.writeInt(level);
        dest.writeDouble(elixir);
        dest.writeString(url);
    }
}