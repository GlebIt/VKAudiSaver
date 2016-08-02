package glebit.com.vkaudisaver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VkAudioArray;


public class MainActivity extends Activity
{
    // TODO: 1. Добавить стрим аудио

    protected static String[] scope=new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.AUDIO};

    protected ListView mAudioListView;
    protected EditText mSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioListView=(ListView)findViewById(R.id.listAudio);
        mSearchBox=(EditText)findViewById(R.id.editSearch);

        mSearchBox.setOnKeyListener(searchBoxOnKeyListener);

        VKSdk.login(this, scope);
    }

    private View.OnKeyListener searchBoxOnKeyListener=new View.OnKeyListener()
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if(event.getAction()==KeyEvent.ACTION_DOWN)
                return false;

            // Если закончили ввод
            if(keyCode==KeyEvent.KEYCODE_ENTER)
            {
                // прячем клавиатуру
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchBox.getWindowToken(), 0);

                // запрос к api на поиск аудио
                VKRequest req=VKApi.audio().search(VKParameters.from(VKApiConst.Q,
                                                                mSearchBox.getText(),
                                                                VKApiConst.COUNT, 200));

                req.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response)
                    {
                        // получаем объект(наследник массива)
                        VkAudioArray audioArr=(VkAudioArray)response.parsedModel;
                        AudioAdapter adapter=new AudioAdapter(MainActivity.this, audioArr);
                        mAudioListView.setAdapter(adapter);
                        super.onComplete(response);
                    }
                });
                return true;
            }

            return false;
        }
    };

    // вызывается после входа
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                //Toast.makeText(getApplicationContext(), "Loged in", Toast.LENGTH_LONG).show();
                // получаем свои аудиозаписи
                VKRequest req=VKApi.audio().get();

                req.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response)
                    {
                        VkAudioArray audioArr=(VkAudioArray)response.parsedModel;
                        AudioAdapter adapter=new AudioAdapter(MainActivity.this, audioArr);
                        mAudioListView.setAdapter(adapter);
                        super.onComplete(response);
                    }
                });
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


