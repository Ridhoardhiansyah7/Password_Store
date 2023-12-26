package com.onedive.passwordstore.uiLayer.activity;

import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.onedive.passwordstore.domainLayer.dataSource.room.app.PasswordDatabase;
import com.onedive.passwordstore.domainLayer.dataSource.room.dao.PasswordRoomDao;


public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    private B binding;

    @Override
    @Deprecated(forRemoval = true)
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    @Deprecated(forRemoval = true)
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setContentView(B binding) {
        this.binding = binding;
        super.setContentView(binding.getRoot());
    }

    public B getBinding() {
        return binding;
    }

    protected PasswordRoomDao getRoomDatabaseDao(){
        return PasswordDatabase.Companion.getInstance(this).dao();
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
