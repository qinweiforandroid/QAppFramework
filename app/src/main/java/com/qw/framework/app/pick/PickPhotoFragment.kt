package com.qw.framework.app.pick

import android.content.Context
import android.view.View
import android.view.ViewGroup
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
import com.qw.recyclerview.core.BaseViewHolder
import com.qw.recyclerview.core.IItemViewType
import com.qw.recyclerview.core.ItemViewDelegate
import com.qw.recyclerview.core.MultiTypeUseCase
import com.qw.recyclerview.template.ListCompat


/**
 * 批量选取图片
 * 1.限定选取图片数量
 * Created by qinwei on 2024/2/20 10:49
 * email: qinwei_it@163.com
 */
class PickPhotoFragment : BaseFragment(R.layout.pick_photo_fragment) {

    private lateinit var mPickList: ListCompat<IItemViewType>
    private lateinit var mMultiTypeUseCase: MultiTypeUseCase

    override fun initView(view: View) {
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.mRecyclerView)
        mMultiTypeUseCase = MultiTypeUseCase()
        mMultiTypeUseCase.register(1, PhotoItemViewDelegate())
        mMultiTypeUseCase.register(2, PhotoAddItemViewDelegate())
        mPickList = object : ListCompat<IItemViewType>(mRecyclerView) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                return mMultiTypeUseCase.getDelegate(viewType).onCreateViewHolder(context!!, parent)
            }

            override fun getItemViewType(position: Int): Int {
                return modules[position].getItemViewType()
            }
        }
        mPickList.setLayoutManager(GridLayoutManager(requireContext(), 3))
    }

    inner class PhotoItemViewDelegate : ItemViewDelegate {
        override fun onCreateViewHolder(context: Context, parent: ViewGroup): BaseViewHolder {
            return object : BaseViewHolder(
                layoutInflater.inflate(R.layout.pick_photo_item_layout,
                    parent,
                    false)
            ) {
                private val mPhotoImg = itemView.findViewById<ImageView>(R.id.mPhotoImg)
                override fun initData(position: Int) {
                    val item = model as PhotoVO
                    Glide.with(this@PickPhotoFragment)
                        .load(item.local)
                        .into(mPhotoImg)
                }
            }
        }
    }

    inner class PhotoAddItemViewDelegate : ItemViewDelegate {
        override fun onCreateViewHolder(context: Context, parent: ViewGroup): BaseViewHolder {
            return object : BaseViewHolder(layoutInflater.inflate(R.layout.pick_photo_item_add_layout,
                parent, false
            )) {
                init {
                    itemView.setOnClickListener {
                        onPhotoAddClick()
                    }
                }

                override fun initData(position: Int) {

                }
            }
        }

        private fun onPhotoAddClick() {
            PictureSelector.create(this@PickPhotoFragment)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        result.forEach {
                            mPickList.modules.add(0, PhotoVO(it.path))
                        }
                        mPickList.adapter.notifyDataSetChanged()
                    }

                    override fun onCancel() {}
                })
        }
    }

    interface IPickVO

    data class PhotoVO(
        val local: String,
        var remote: String = "",
        var remoteId: String = ""
    ) : IPickVO, IItemViewType {
        override fun getItemViewType() = 1
    }

    class AddVO : IPickVO, IItemViewType {
        override fun getItemViewType() = 2
    }

    override
    fun initData() {
        mPickList.modules.add(AddVO())
        mPickList.adapter.notifyDataSetChanged()
    }
}