package com.manish.shaadi.binding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import javax.inject.Inject


/** @author Manish patel on 28/09/19*/
class FragmentBindingAdapters @Inject constructor(private val fragment: Fragment) {

    /** Attribute "app:imageUrl" will help you to load image (local or image URL) on ImageView */
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(fragment).load(url).into(imageView)
    }


    /** Attribute "app:visibility" will help you to change the view visibility if condition is true then it will be visible else gone. */
    @BindingAdapter("visibility")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter(value = ["drawable", "drawableVisible"], requireAll = true)
    fun showDrawableEnd(view: View, drawable: Drawable, drawableVisible: Boolean) {
        if (drawableVisible) {
            (view as TextView).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        } else {
            (view as TextView).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }


//    /** Attribute "app:cameraCode" to open Camera2 and Attribute "app:galleryCode" for open gallery. */
//    @BindingAdapter(value = ["cameraCode", "galleryCode"], requireAll = false)
//    fun takePicture(view: View, cameraCode: Int?, galleryCode: Int?) {
//        val context = view.context
//        val dialog = TakePictureDialog(context, object : TakePictureDialog.DialogItemClickListener {
//            override fun onCameraClick() {
//                openCamera(
//                    context,
//                    cameraCode ?: throw Exception("Camera code should be specified in view binding")
//                )
//            }
//
//            override fun onGalleryClick() {
//                openGallery(
//                    galleryCode
//                        ?: throw Exception("Gallery code should be specified in view binding")
//                )
//            }
//        })
//
//        view.setOnClickListener {
//            when {
//                cameraCode != null && galleryCode != null -> dialog.show()
//                galleryCode != null -> openGallery(galleryCode)
//                cameraCode != null -> openCamera(context, cameraCode)
//            }
//        }
//    }
//
//
//    /** To open Camera2, we pass "camera_2" in intent.putExtra.
//     *  if you want to open normal camera then pass "camera". */
//    private fun openCamera(context: Context, code: Int) {
//        if (checkCameraHardware(fragment.activity)) {
//            if (getInternalStorageSize() > 5) {
//                val intent = Intent(fragment.activity, ImageViewerActivity::class.java)
//                intent.putExtra("quality", 40)
//                intent.putExtra("code", "camera_2")
//                fragment.startActivityForResult(intent, code)
//            } else {
//                Toast.makeText(context, context.getString(R.string.noMemory), Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }

    private fun openGallery(code: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        fragment.startActivityForResult(intent, code)
    }
}