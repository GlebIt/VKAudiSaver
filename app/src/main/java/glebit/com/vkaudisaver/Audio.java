package glebit.com.vkaudisaver;

/**
 * Created by Gleb on 21.04.2016.
 */
public class Audio
{
    private long mId;
    private String mTitle;
    private String mArtist;
    private long mDuration;
    private String mUrl;

    public Audio(long id, String title, String artist, long duration, String url)
    {
        setId(id);
        setTitle(title);
        setArtist(artist);
        setDuration(duration);
        setUrl(url);
    }

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        mId = id;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public String getArtist()
    {
        return mArtist;
    }

    public void setArtist(String artist)
    {
        mArtist = artist;
    }

    public long getDuration()
    {
        return mDuration;
    }

    public void setDuration(long duration)
    {
        mDuration = duration;
    }

    public String getUrl()
    {
        return mUrl;
    }

    public void setUrl(String url)
    {
        mUrl = url;
    }
}
