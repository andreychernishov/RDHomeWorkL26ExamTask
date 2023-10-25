package com.example.rdhomework26.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.rdhomework26.OnAuthLaunch
import com.example.rdhomework26.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class SplashFragment: Fragment() {

    private val animatorSet = AnimatorSet()
    private var signInButton: SignInButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val icon: ImageView = view.findViewById(R.id.splash_screen_icon)
        signInButton = view.findViewById(R.id.sign_in_button)

        startAnimation(icon)

        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("546741936814-j3mtarn9bhvvu57r4eokqj9aomh8i1is.apps.googleusercontent.com")
                .requestEmail()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val activity = requireActivity() as OnAuthLaunch

        if (account == null){
            showSignInButton()
        }else{
            activity.showListFragment()
            animatorSet.cancel()
        }

        signInButton?.setOnClickListener {
            activity.launch(googleSignInClient.signInIntent)
        }

    }

    private fun showSignInButton(){
        signInButton?.visibility = View.VISIBLE
        animatorSet.cancel()
    }

    private fun startAnimation(image: ImageView){
        val scaleXAnimation = ObjectAnimator.ofFloat(image, View.SCALE_X, 0.5f, 1f)
        scaleXAnimation.repeatMode = ObjectAnimator.REVERSE
        scaleXAnimation.repeatCount = ObjectAnimator.INFINITE

        val scaleYAnimation = ObjectAnimator.ofFloat(image, View.SCALE_Y, 0.5f, 1f)
        scaleYAnimation.repeatMode = ObjectAnimator.REVERSE
        scaleYAnimation.repeatCount = ObjectAnimator.INFINITE

        animatorSet.playTogether(scaleXAnimation,scaleYAnimation)
        animatorSet.duration = 1500
        animatorSet.start()
    }
}