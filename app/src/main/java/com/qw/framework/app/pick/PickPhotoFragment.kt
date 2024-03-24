package com.qw.framework.app.pick

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.qw.framework.app.R
import com.qw.framework.ui.BaseFragment
import com.qw.recyclerview.core.AbsItemViewDelegate
import com.qw.recyclerview.core.BaseViewHolder
import com.qw.recyclerview.core.IItemViewType
import com.qw.recyclerview.template.ListCompat


/**
 * 批量选取图片
 * 1.限定选取图片数量
 * Created by qinwei on 2024/2/20 10:49
 * email: qinwei_it@163.com
 */
class PickPhotoFragment : BaseFragment(R.layout.pick_photo_fragment) {

    private lateinit var mPickList: ListCompat<IItemViewType>

    companion object {
        const val PHOTO_MAX_SIZE = 9
    }

    private val selects = ArrayList<LocalMedia>()
    override fun initView(view: View) {
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.mRecyclerView)
        mPickList = ListCompat.MultiTypeBuilder()
            .register(1, PhotoItemViewDelegate())
            .register(2, PhotoAddViewDelegate())
            .create(mRecyclerView)
        mPickList.setLayoutManager(GridLayoutManager(requireContext(), 3))
    }

    inner class PhotoItemViewDelegate : AbsItemViewDelegate(R.layout.pick_photo_item_layout) {
        override fun onCreateViewHolder(view: View) = object : BaseViewHolder(view) {
            private val mPhotoImg = itemView.findViewById<ImageView>(R.id.mPhotoImg)
            override fun initData(position: Int) {
                val item = model as PhotoVO
                Glide.with(this@PickPhotoFragment)
                    .load(item.getPath())
                    .into(mPhotoImg)
            }
        }
    }

    inner class PhotoAddViewDelegate : AbsItemViewDelegate(R.layout.pick_photo_item_add_layout) {
        override fun onCreateViewHolder(view: View) = object : BaseViewHolder(view) {
            init {
                itemView.setOnClickListener {
                    onPhotoAddClick()
                }
            }

            override fun initData(position: Int) {

            }
        }

        private fun onPhotoAddClick() {
            PictureSelector.create(this@PickPhotoFragment)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(PHOTO_MAX_SIZE)
                .setSelectedData(selects)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        selects.clear()
                        selects.addAll(result)
                        notifyPhotoSelectChanged()
                    }

                    override fun onCancel() {}
                })
        }
    }

    private fun notifyPhotoSelectChanged() {
        mPickList.modules.clear()
        selects.forEach {
            mPickList.modules.add(PhotoVO(it))
        }
        if (selects.size < PHOTO_MAX_SIZE) {
            mPickList.modules.add(AddVO())
        }
        mPickList.adapter.notifyDataSetChanged()
    }

    data class PhotoVO(
        val localMedia: LocalMedia
    ) : IItemViewType {
        override fun getItemViewType() = 1
        fun getPath(): String {
            return localMedia.path
        }
    }

    class AddVO : IItemViewType {
        override fun getItemViewType() = 2
    }

    override
    fun initData() {
        notifyPhotoSelectChanged()
    }
}