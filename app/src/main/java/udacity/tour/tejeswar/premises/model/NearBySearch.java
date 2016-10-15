package udacity.tour.tejeswar.premises.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tejeswar on 10/16/2016.
 */
public class NearBySearch
{

    @SerializedName("location")
    private List<String> Degrees = new ArrayList<String>();

    @SerializedName("icon")
    private String Icon;

    @SerializedName("id")
    private long Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("open_now")
    private Boolean Timings;

    @SerializedName("rating")
    private Integer Rating;

    @SerializedName("vicinity")
    private String Landmark;

    public NearBySearch(List<String> degrees, String icon, long id, String landmark, String name, Integer rating, Boolean timings) {
        Degrees = degrees;
        Icon = icon;
        Id = id;
        Landmark = landmark;
        Name = name;
        Rating = rating;
        Timings = timings;
    }

    public List<String> getDegrees() {
        return Degrees;
    }

    public void setDegrees(List<String> degrees) {
        Degrees = degrees;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating(Integer rating) {
        Rating = rating;
    }

    public Boolean getTimings() {
        return Timings;
    }

    public void setTimings(Boolean timings) {
        Timings = timings;
    }

}
