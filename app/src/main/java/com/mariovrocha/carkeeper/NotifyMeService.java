package com.mariovrocha.carkeeper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.mariovrocha.carkeeper.Model.User;
import com.mariovrocha.carkeeper.Utility.NotificationUtils;

import static com.mariovrocha.carkeeper.Utility.NotificationUtils.getBitmapFromURL;

public class NotifyMeService extends IntentService {

    public static final int NOTIFYID = 1;
    public static final String AGREEEXTRA = "AGREEEXTRA";

    private DatabaseReference rootRef,checkRef;
    private Context context;
    private String friendID,friendName,meetID,userUID,friendPhotoURL ;
    private boolean agree;
    public NotifyMeService() {
        super("NotifyMeService");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rootRef = FirebaseDatabase.getInstance().getReference();
                context = getApplicationContext();
                userUID = intent.getStringExtra(ListFriendActivity.MYUIDEXTRAINTENT);
                checkRef = rootRef.child("invite").child(userUID);
                checkRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            friendID = (String) dataSnapshot.child("inviter").getValue();
                            agree = (Boolean) dataSnapshot.child("agree").getValue();
                            meetID = (String) dataSnapshot.child("meetID").getValue();
                            rootRef.child("users").child(friendID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    friendName = user.getName();
                                    friendPhotoURL = user.getPhotoURL();
                                    if (!agree) {
                                        new AsyncTask<Void,Void,Bitmap>() {
                                            @Override
                                            protected Bitmap doInBackground(Void... params) {
                                                return getBitmapFromURL(friendPhotoURL);
                                            }

                                            @Override
                                            protected void onPostExecute(Bitmap bitmap) {
                                                NotificationUtils.NotifyMe(friendName, friendID, meetID, bitmap,context,userUID);
                                            }
                                        }.execute();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }else{
                            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                            manager.cancel(NOTIFYID);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }).run();
    }
}
