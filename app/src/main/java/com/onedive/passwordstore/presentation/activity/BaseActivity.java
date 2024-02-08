package com.onedive.passwordstore.presentation.activity;

import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.onedive.passwordstore.data.local.room.app.PasswordDatabase;
import com.onedive.passwordstore.data.local.room.dao.PasswordRoomDao;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;

/**
 * This class is used as the base for every activity in this project, this class initiates viewBinding
 * and get an instance of the room database
 * @param <B> viewBinding to use
 */
public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    private B binding;

    @Override
    @Deprecated(level = DeprecationLevel.ERROR,message = "Please use viewBinding!")
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    @Deprecated(level = DeprecationLevel.ERROR,message = "Please use viewBinding!")
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     *android.app.Activity Set the activity content to an explicit view.
     *This view is placed directly into the activity's view hierarchy
     * @param binding viewBinding to use
     */
    public void setContentView(B binding) {
        this.binding = binding;
        super.setContentView(binding.getRoot());
    }

    /**
     *
     * @return viewBinding
     */
    public B getBinding() {
        return binding;
    }

    /**
     * gets the database instance, and returns the dao
     * @return DAO
     * @see PasswordDatabase
     * @see PasswordRoomDao
     */
    public PasswordRoomDao getRoomDatabaseDao(){
        return PasswordDatabase.getInstance(this).dao();
    }


    @SuppressWarnings("unused")
    protected void onBackPressedClicked() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
}
