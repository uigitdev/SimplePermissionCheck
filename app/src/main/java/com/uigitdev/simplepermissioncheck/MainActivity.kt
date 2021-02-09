package com.uigitdev.simplepermissioncheck

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.uigitdev.simplepermissioncheck.tool.MPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var permission: MPermission

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPermission()
    }

    private fun initPermission() {
        Log.d(TAG, "initPermission: method call")
        //Init MPermission
        permission = MPermission(this)

        //Add your permission to the list. Also enter it in a manifest file.
        permission.addPermission(
                MPermission.MPermissionObject(
                        1234,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
        )
        permission.addPermission(
                MPermission.MPermissionObject(
                        1221,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
        )

        setFineLocationPermission()
        setFilePermission()
    }

    private fun setFilePermission() {
        item_permission_2.setOnClickListener {

            permission.checkPermission(
                    this,
                    permission.permissions[1],
                    object : MPermission.MPermissionListener {
                        override fun showInfoDialog(permissionObject: MPermission.MPermissionObject) {
                            Log.d(
                                    TAG,
                                    "showInfoDialog: ${permissionObject.PERMISSION} ${permissionObject.REQUEST_CODE}"
                            )
                            permission.setAlertDialog(
                                    this@MainActivity,
                                    "Title",
                                    "text",
                                    permissionObject
                            )
                        }

                        override fun permissionValue(isGranted: Boolean) {
                            Log.d(TAG, "permissionValue: $isGranted")
                        }
                    })
        }
    }

    private fun setFineLocationPermission() {
        Log.d(TAG, "setPermission: method call")
        item_permission_1.setOnClickListener {

            //permission.permissions[0] item of your own permission list.
            permission.checkPermission(
                    this,
                    permission.permissions[0],
                    object : MPermission.MPermissionListener {
                        override fun showInfoDialog(permissionObject: MPermission.MPermissionObject) {
                            //If the permission request is called again.
                            Log.d(
                                    TAG,
                                    "showInfoDialog: ${permissionObject.PERMISSION} ${permissionObject.REQUEST_CODE}"
                            )
                            permission.setAlertDialog(
                                    this@MainActivity,
                                    "Title",
                                    "text",
                                    permissionObject
                            )
                        }

                        override fun permissionValue(isGranted: Boolean) {
                            //If the user grants permission.
                            Log.d(TAG, "permissionValue: $isGranted")
                        }
                    })
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {

        when (requestCode) {
            permission.permissions[0].REQUEST_CODE -> {
                Log.d(TAG, "onRequestPermissionsResult: the user has approved the permission.")
            }
            permission.permissions[1].REQUEST_CODE -> {
                Log.d(TAG, "onRequestPermissionsResult: the user has approved the permission.")
            }

        }
    }
}