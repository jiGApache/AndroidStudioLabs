package com.example.lab2

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPageAdapter internal constructor(fm: FragmentManager, val objects: MutableList<Technology>) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val arguments = Bundle()
        arguments.putString("name", objects[position].name)
        arguments.putString(
            "helpText",
            objects[position].helpText
        )
        arguments.putParcelable("bitmap", objects[position].bitmap)

        val myFragment = MyFragmentElement()
        myFragment.setArguments(arguments)

        return myFragment
    }

    override fun getCount(): Int {
        return objects.size
    }
}