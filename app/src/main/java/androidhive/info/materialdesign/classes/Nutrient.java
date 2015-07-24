package androidhive.info.materialdesign.classes;

import java.io.Serializable;

/**
 * Created by Marco on 11/07/2015.
 */
public class Nutrient implements Serializable
{
    private String name;
    private String  value;

    public Nutrient(String name, String value)
    {
        super();
        this.name = name;
        this.value = value;
    }

    // getter and setter name
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    // getter and setter value
    public void setValue(String value)
    {
        this.value = value;
    }
    public String getValue()
    {
        return value;
    }
}
