package com.weight68kg.sample

import android.Manifest
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.weight68kg.droidcore.marshmallow.PermissionActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by weight68kg on 2018/9/10.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener {
            PermissionActivity.start(this@MainActivity, "开启权限", object : PermissionActivity.PermissionCallback {
                override fun hasPermission() {
                    Toast.makeText(this@MainActivity, "有权限", Toast.LENGTH_SHORT).show()
                }

                override fun noPermission() {
                    Toast.makeText(this@MainActivity, "无", Toast.LENGTH_SHORT).show()
                }
            }, Manifest.permission.CAMERA)
        }

    }
}