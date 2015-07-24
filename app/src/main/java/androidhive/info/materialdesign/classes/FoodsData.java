package androidhive.info.materialdesign.classes;

import java.io.Serializable;
import java.util.List;

public class FoodsData implements Serializable
{
    public static List<Food> foodsData = null;

    // constructor

    public FoodsData (List<Food> arrayList)
    {
        foodsData = arrayList;
    }

    public static List<Food> getFoodsData()
    {
        return foodsData;
    }

    public static void setFoodsData(List<Food> foodsData)
    {
        FoodsData.foodsData = foodsData;
    }
}
