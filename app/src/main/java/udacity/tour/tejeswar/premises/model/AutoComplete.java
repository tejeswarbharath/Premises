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

    public class Term
    {

        private int offset;
        private String value;

    }

    @SerializedName("terms")
    List<Term> list = new ArrayList<>();

    @SerailizedNamed("types")
    List<String> Types = new ArrayList<String>();

    public AutoComplete(String description, long id, List<Term> list, List<Integer> substring, List<String> types)
    {

        Description = description;
        Id = id;
        this.list = list;
        Substring = substring;
        Types = types;
    }

    public List<String> getTypes() {
        return Types;
    }

    public void setTypes(List<String> types) {
        Types = types;
    }

    public List<Integer> getSubstring() {
        return Substring;
    }

    public void setSubstring(List<Integer> substring) {
        Substring = substring;
    }

    public List<Term> getList() {
        return list;
    }

    public void setList(List<Term> list) {
        this.list = list;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
