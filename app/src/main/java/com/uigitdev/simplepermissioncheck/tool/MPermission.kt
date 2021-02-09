package com.uigitdev.simplepermissioncheck.tool

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MPermission(private val activity: Activity) {
    val permissions: ArrayList<MPermissionObject> = ArrayList()

    fun addPermission(permission: MPermissionObject){
        permissions.add(permission)
    }

    fun checkPermission(activity: Activity, permissionObject: MPermissionObject, listener: MPermissionListener){
        if (ContextCompat.checkSelfPermission(activity, permissionObject.PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionObject.PERMISSION)) {
                listener.showInfoDialog(permissionObject)
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permissionObject.PERMISSION), permissionObject.REQUEST_CODE)
            }
        } else {
            listener.permissionValue(true)
        }
    }

    private fun callRequest(permissionObject: MPermissionObject){
        ActivityCompat.requestPermissions(activity, arrayOf(permissionObject.PERMISSION), permissionObject.REQUEST_CODE)
    }

    interface MPermissionListener{
        fun showInfoDialog(permissionObject: MPermissionObject)

        fun permissionValue(isGranted: Boolean)
    }

    class MPermissionObject(val REQUEST_CODE: Int, val PERMISSION: String)

    fun setAlertDialog(activity: Activity, title: String, text: String, permissionObject: MPermissionObject) {
        val alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(text)
        alertBuilder.setPositiveButton("Yes") { dialog, which ->
            callRequest(permissionObject)
        }
        val alert = alertBuilder.create()
        alert.show()
    }
}