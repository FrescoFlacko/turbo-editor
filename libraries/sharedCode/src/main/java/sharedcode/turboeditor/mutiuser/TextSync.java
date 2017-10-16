package sharedcode.turboeditor.mutiuser;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import sharedcode.turboeditor.activity.MainActivity;

/**
 * Created by Adam on 2017-02-15.
 */

public class TextSync {

    public static String key;

    public static void pushText(String text)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(key);

        myRef.child("content").setValue(text);
    }

    public static void pushCaretPosition(int position)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(key);

        myRef.child("users").child(android.os.Build.DEVICE).setValue(position);
    }

    public static void removeCaretPosition()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(key);

        myRef.child("users").child(android.os.Build.DEVICE).removeValue();
    }
    public static void addCaretListener(final MainActivity.Editor mEditor)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(key).child("users");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int inputType = mEditor.getInputType();

                mEditor.positions.add(dataSnapshot.getValue(Integer.class));
                mEditor.realPositions.put(dataSnapshot.getKey(), dataSnapshot.getValue(Integer.class));

                mEditor.setInputType(inputType);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int inputType = mEditor.getInputType();

                mEditor.realPositions.put(dataSnapshot.getKey(), dataSnapshot.getValue(Integer.class));

                mEditor.setInputType(inputType);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mEditor.realPositions.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addPullListener(final MainActivity ma)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(key).child("content");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("CUSTOMTURBO", "Value is: " + value);
                ma.changeText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("CUSTOMTURBO", "Failed to read value.", error.toException());
            }
        });
    }

}
