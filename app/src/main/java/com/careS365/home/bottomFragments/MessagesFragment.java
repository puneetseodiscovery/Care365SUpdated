package com.careS365.home.bottomFragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.util.Constants;
import com.careS365.util.Utility;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    /*@BindView(R.id.rv_messages)
    RecyclerView rvMessages;
    ArrayList<BeanMessages> messagesArrayList ;*/

    @BindView(R.id.tv_title)
    TextView tvTitle;

    Context context;


    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference;
    String circleName;


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);
        context = this.getActivity();

        layout = (LinearLayout) view.findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)view.findViewById(R.id.layout2);
        sendButton = (ImageView)view.findViewById(R.id.sendButton);
        messageArea = (EditText)view.findViewById(R.id.messageArea);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);


        init();
        return view;
    }

    private void init() {

        for(Map.Entry entry: Constants.circlesHashmap.entrySet()){
            if(entry.getKey().equals(Constants.SELECTED_CIRCLE)){
                circleName = entry.getValue().toString();
                tvTitle.setText(circleName);
            }
        }

        Firebase.setAndroidContext(context);
        reference = new Firebase("https://care365-29dbd.firebaseio.com/messages/" + circleName + "_" + Constants.SELECTED_CIRCLE);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", Utility.getUserName());
                    reference.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(Utility.getUserName())){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    //addMessageBox(firebaseChatWith + ":-\n" + message, 2);
                    addMessageBox(userName + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*messagesArrayList=new ArrayList<>();

        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Sapna","01:30"));
        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Puneet","01:30"));
        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Taran","01:30"));
        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Gagan","01:30"));
        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Mohit","01:30"));
        messagesArrayList.add(new BeanMessages(R.mipmap.messages_profile_pic,"You : Hello","Atul","01:30"));

        rvMessages.setLayoutManager(new LinearLayoutManager(context));
        MessagesAdapter adapter = new MessagesAdapter(context, messagesArrayList);
        rvMessages.setAdapter(adapter);*/
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(context);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}
