package com.br.valber.testegitapi.presentation.javarepo.adapter.viewholder

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class JavaRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val repoName: TextView = view.findViewById(R.id.textViewRepoName)
    private val repoDescription: TextView = view.findViewById(R.id.textViewRepoDescription)
    private val countBranch: TextView = view.findViewById(R.id.textViewCountBranch)
    private val countStar: TextView = view.findViewById(R.id.textViewCountStar)
    private val userName: TextView = view.findViewById(R.id.textViewUserName)
    private val imageViewUser: ImageView = view.findViewById(R.id.imageViewUser)

    fun bind(itemJava: ItemJava?) {
        showItems(itemJava)
    }

    private fun showItems(itemJava: ItemJava?) {
        repoName.text = itemJava?.fullName
        repoDescription.apply {
            isVisible = itemJava?.description != null
            text = itemJava?.description
        }
        countStar.text = itemJava?.startCount
        countBranch.text = itemJava?.forksCount
        userName.text = itemJava?.name

        Picasso.get()
            .load(itemJava?.avatar)
            .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
            .transform(CircleTransform())
            .into(imageViewUser)
    }

    companion object {

        fun create(parent: ViewGroup): JavaRepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_java_repo, parent, false)
            return JavaRepoViewHolder(view)
        }

    }

    class CircleTransform : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val size = source.width.coerceAtMost(source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2
            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
            if (squaredBitmap != source) {
                source.recycle()
            }
            val bitmap = Bitmap.createBitmap(size, size, source.config)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(
                squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            paint.shader = shader
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            squaredBitmap.recycle()
            return bitmap
        }

        override fun key(): String {
            return "circle"
        }
    }
}