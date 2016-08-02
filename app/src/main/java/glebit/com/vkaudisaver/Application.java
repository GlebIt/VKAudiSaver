package glebit.com.vkaudisaver;

import com.vk.sdk.VKSdk;

/**
 * Created by Gleb on 24.03.2016.
 */
public class Application extends android.app.Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
