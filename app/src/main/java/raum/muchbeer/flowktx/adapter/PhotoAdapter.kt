package raum.muchbeer.flowktx.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.flowktx.adapter.PhotoAdapter.*
import raum.muchbeer.flowktx.databinding.ListItemPhotoBinding
import raum.muchbeer.flowktx.model.UnsplashPhoto

class PhotoAdapter : PagingDataAdapter<UnsplashPhoto, PhotoVH >(diffCallback) {


    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoVH(binding )
    }

    class PhotoVH(private val binding: ListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.photo?.let { photo ->
                    val uri = Uri.parse(photo.user.attributionUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    view.context.startActivity(intent)
                }
            }
        }
        fun bind(item: UnsplashPhoto) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }


    }

    companion object diffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem == newItem
        }
    }
}