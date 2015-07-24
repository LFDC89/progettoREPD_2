package androidhive.info.materialdesign.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marco on 11/07/2015.
 */
public class Food implements Serializable
{
    private String 		   description;
    private List<Nutrient> nutList 	    = null;

    // constructor
    public Food(String description, List<Nutrient> nutList)
    {
        super();
        this.description   = description;
        this.nutList       = nutList;
    }

    // getter and setter of description
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    // getter and setter nutList
    public List<Nutrient> getNutList()
    {
        return nutList;
    }
    public void setNutList(List<Nutrient> nutList)
    {
        this.nutList = nutList;
    }
}
