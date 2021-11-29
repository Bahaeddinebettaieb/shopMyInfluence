package com.smi.test.presentation.signIn

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.smi.test.presentation.home.HomeActivity
import com.smi.test.R
import com.smi.test.presentation.App.Companion.context
import com.smi.test.presentation.Const
import com.smi.test.presentation.entites.User
import com.smi.test.presentation.signUp.SignUpActivity
import com.smi.test.presentation.utils.GlobalUtils
import com.smi.test.presentation.utils.StringValidatorsUtils
import io.paperdb.Paper
import java.util.*

class SignInActivity : AppCompatActivity() {
    private val TAG = "SignInActivity"
    var state: Boolean = false
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var signInBtn: TextView? = null
    var signUpBtn: TextView? = null
    var togglePassword: ImageView? = null
    var userModel: User? = null
    private var firebaseAuth: FirebaseAuth? = null
    private val mAuthListener: FirebaseAuth.AuthStateListener? = null
    var dialogLoadingProgress: Dialog? = null


    var passwordText: String? = null
    var emailText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        emailEditText = findViewById<EditText>(R.id.email_et)
        passwordEditText = findViewById<EditText>(R.id.password_et)
        signUpBtn = findViewById<TextView>(R.id.sign_up_tv)
        signInBtn = findViewById<TextView>(R.id.sign_in_tv)
        togglePassword = findViewById<ImageView>(R.id.iv_pwd)

        firebaseAuth = FirebaseAuth.getInstance()

        togglePassword!!.setOnClickListener {
            changeTogglePassword()
        }
        signInBtn!!.setOnClickListener {
            startSignIn()
        }
        signUpBtn!!.setOnClickListener {
            navigateToSignUp()
        }
    }

    fun changeStateTogglePassword(state: Boolean) {
        if (state) {
            Log.e(TAG, "changeStateTogglePassword: is open ")
            togglePassword!!.setImageResource(R.drawable.ic_eye_view_open)
            passwordEditText!!.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            Log.e(TAG, "changeStateTogglePassword: is closed ")
            togglePassword!!.setImageResource(R.drawable.ic_eye_view)
            passwordEditText!!.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    fun changeTogglePassword(){
        state=!state
        if (state) {
            state=true
            changeStateTogglePassword(true)
        } else {
            state=false
            changeStateTogglePassword(false)
        }
    }

    fun navigateToSignUp(){
        GlobalUtils.navigateToActivity(
            this,
            this,
            SignUpActivity::class.java
        )
    }

    fun checkFields():Boolean{
        emailText = emailEditText!!.text.toString().trim { it <= ' ' }
        passwordText = passwordEditText!!.text.toString().trim { it <= ' ' }
        when{
            emailText.isNullOrBlank() ->{
                Toast.makeText(this, context.getString(R.string.empty_field),Toast.LENGTH_SHORT).show()
                return false
            }
            !StringValidatorsUtils.isValidEmail(emailText) ->{
                Toast.makeText(this, context.getString(R.string.incorrect_email),Toast.LENGTH_SHORT).show()
                return false
            }
            passwordText.isNullOrBlank() ->{
                Toast.makeText(this, context.getString(R.string.empty_field),Toast.LENGTH_SHORT).show()
                return false
            }
            passwordText!!.length < 6 ->{
                Toast.makeText(this, context.getString(R.string.incorrect_password),Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun startSignIn() {
        emailText = emailEditText!!.text.toString()
        passwordText = passwordEditText!!.text.toString()
        if (checkFields()){
            showProgressLoadingDialog()
            firebaseAuth!!.signInWithEmailAndPassword(emailText!!, passwordText!!).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    hideProgressLoadingDialog()
                    if (task.isSuccessful) {
                        val user: FirebaseUser = firebaseAuth!!.currentUser!!
                        if (task.result!!.additionalUserInfo!!.isNewUser) {
                            val email = user.email
                            val uid = user.uid
                            val hashMap = HashMap<Any, String?>()
                            hashMap["email"] = email
                            hashMap["uid"] = uid
                            hashMap["username"] = userModel!!.username
                            hashMap["password"] = userModel!!.password
                            val database = FirebaseDatabase.getInstance()
                            val mDatabase = database.getReference("users")
                            mDatabase.child(uid).setValue(hashMap)
                        }

                        val firebaseUser: FirebaseUser = firebaseAuth!!.currentUser!!
                        Log.e(TAG, "startSignIn: "+firebaseUser.uid)
                        Paper.book().write(Const.USER_INFO,firebaseUser.uid)
                        GlobalUtils.navigateToActivity(
                            this,
                            this,
                            HomeActivity::class.java
                        )
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }).addOnFailureListener(OnFailureListener { e ->
                hideProgressLoadingDialog()
                Toast.makeText(
                    this, e.message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    fun showProgressLoadingDialog() {
        try {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.progress_bar_dialog, null, false)
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)
            alertDialogBuilder.setView(view)
            dialogLoadingProgress = alertDialogBuilder.create()
            dialogLoadingProgress!!.setCanceledOnTouchOutside(false)
            dialogLoadingProgress!!.setCancelable(false)
            dialogLoadingProgress!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogLoadingProgress!!.show()
            dialogLoadingProgress!!.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        } catch (e: Exception) {
            Log.e(TAG, "hideProgressLoadingDialog: $e")
        }
    }

    fun hideProgressLoadingDialog() {
        try {
            (dialogLoadingProgress as AlertDialog?)!!.dismiss()
        } catch (e: Exception) {
            Log.e(TAG, "hideProgressLoadingDialog: $e")
        }
    }
}