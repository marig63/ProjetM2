package guillaume.marisa.projetm2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guillaume on 01/12/2017.
 */

public class ObjectResponse implements Parcelable {
    private final String name;
    private final int id;
    private final String admin;
    private final double lat;
    private final double lon;
    private final String zone;

    public ObjectResponse(String name, int id, String admin,double lat, double lon, String zone) {
        this.name = name;
        this.id = id;
        this.admin = admin;
        this.lat = lat;
        this.lon = lon;
        this.zone = zone;
    }

    protected ObjectResponse(Parcel in) {
        name = in.readString();
        id = in.readInt();
        admin = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        zone = in.readString();
    }

    public static final Creator<ObjectResponse> CREATOR = new Creator<ObjectResponse>() {
        @Override
        public ObjectResponse createFromParcel(Parcel in) {
            return new ObjectResponse(in);
        }

        @Override
        public ObjectResponse[] newArray(int size) {
            return new ObjectResponse[size];
        }
    };

    public String name() {
        return name;
    }

    public int id() {
        return id;
    }

    public String admin() {
        return admin;
    }

    public double lat(){ return lat;}

    public double lon(){return  lon;}

    public String zone() {
        return zone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(admin);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
        parcel.writeString(zone);
    }
}
