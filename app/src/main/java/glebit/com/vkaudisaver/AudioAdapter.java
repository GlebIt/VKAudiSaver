package glebit.com.vkaudisaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VkAudioArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gleb on 22.04.2016.
 */
public class AudioAdapter extends ArrayAdapter<VKApiAudio>
{
    // TODO: 2. Добавить прогрэсбар

    protected Context mContext;
    protected List<VKApiAudio> mAudios;

    public AudioAdapter(Context context, VkAudioArray vkAudioArray)
    {
        super(context, R.layout.audio_list_item, convertToList(vkAudioArray));
        mAudios=convertToList(vkAudioArray);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.audio_list_item, null);
            holder = new ViewHolder();
            holder.titleTextView=(TextView)convertView.findViewById(R.id.textTrackName);
            holder.artistTextView=(TextView)convertView.findViewById(R.id.textArtist);
            holder.durationTextView=(TextView)convertView.findViewById(R.id.textTime);
            holder.downloadImageView=(ImageView)convertView.findViewById(R.id.imageDownload);

            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        final VKApiAudio audio=mAudios.get(position);

        holder.titleTextView.setText(audio.title);
        holder.artistTextView.setText(audio.artist);
        holder.durationTextView.setText(convertDuration(audio.duration));

        holder.downloadImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // начинаем загрузку
                new AudioDownloader(holder).execute(audio.url, audio.title);
            }
        });

        return convertView;
    }

    private String convertDuration(int duration)
    {
        SimpleDateFormat formatter=new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(new Date(duration*1000));
    }

    // превращаем объект в список(возожно есть способ лучше?)
    private static final List<VKApiAudio> convertToList(VkAudioArray array)
    {
        ArrayList<VKApiAudio> lst=new ArrayList<VKApiAudio>();
        int count=array.size();

        for(int i=0; i<count; i++)
        {
            lst.add(array.get(i));
        }

        return  lst;
    }

    public class ViewHolder
    {
        TextView titleTextView;
        TextView artistTextView;
        TextView durationTextView;
        ImageView downloadImageView;
    }
}
