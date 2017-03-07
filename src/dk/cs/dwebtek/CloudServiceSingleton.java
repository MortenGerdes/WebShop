package dk.cs.dwebtek;

/**
 * Created by morten on 2/23/17.
 */
public class CloudServiceSingleton
{
    private static CloudService instance = null;
    private CloudServiceSingleton()
    {
        // rawr XD
    }
    public static CloudService getInstance()
    {
        if(instance == null)
        {
            instance = new CloudService();
        }
        return instance;
    }
}
