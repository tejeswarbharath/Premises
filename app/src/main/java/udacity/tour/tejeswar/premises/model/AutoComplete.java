package udacity.tour.tejeswar.premises.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tejeswar on 10/16/2016.
 */

public class AutoComplete
{

    @SerailizedName("description")
    private String Description;

    @SerializedName("id")
    private long Id;

    @SerializedName("matched_substrings")
    private List<Integer> Substring = new ArrayList<Integer>();

    @SerializedName("terms")
    List<Object> list = new ArrayList<Object>();

    for (Object obj: list)
    {
        if (obj instanceof Integer)
        {
            @SerializedName("offset")
            private ArrayList<Integer> Offset = new ArrayList<Integer>();
            listOfMixedTypes.add(Offset);
        }
        else if (obj instanceof String)
        {
            @SerializedName("value")
            private ArrayList<String> Value = new ArrayList<String>();
            listOfMixedTypes.add(Value);
        }
    }

    public AutoComplete(String description, long id, List<Object> list, Object obj, List<Integer> substring) {
        Description = description;
        Id = id;
        this.list = list;
        this.obj = obj;
        Substring = substring;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<Integer> getSubstring() {
        return Substring;
    }

    public void setSubstring(List<Integer> substring) {
        Substring = substring;
    }
}
