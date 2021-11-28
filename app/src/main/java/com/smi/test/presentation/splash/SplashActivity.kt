package com.smi.test.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.smi.test.presentation.home.HomeActivity
import com.smi.test.R
import com.smi.test.presentation.Const
import com.smi.test.presentation.signIn.SignInActivity
import com.smi.test.presentation.utils.GlobalUtils
import io.paperdb.Paper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Paper.book().contains(Const.USER_INFO)) {
//            var currentUser.set((Paper.book().read(Const.USER_INFO) as UserResponse).user)
            navigateToMain()
        } else {
            navigateToLogin()
        }
    }

    fun navigateToMain() {
        Handler().postDelayed({
            GlobalUtils.navigateToActivity(
                this, this,
                HomeActivity::class.java
            )
        }, 1000)
    }

    fun navigateToLogin() {
        Handler().postDelayed({
            GlobalUtils.navigateToActivity(
                this, this,
                SignInActivity::class.java
            )
        }, 1000)
    }
}