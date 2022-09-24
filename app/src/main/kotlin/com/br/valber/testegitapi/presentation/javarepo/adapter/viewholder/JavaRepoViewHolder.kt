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
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.presentation.pullrequest.view.PullRequestActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class JavaRepoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val repoName: TextView = view.findViewById(R.id.textViewRepoName)
    private val repoDescription: TextView = view.findViewById(R.id.textViewRepoDescription)
    private val countBranch: TextView = view.findViewById(R.id.textViewCountBranch)
    private val countStar: TextView = view.findViewById(R.id.textViewCountStar)
    private val userName: TextView = view.findViewById(R.id.textViewUserName)
    private val imageViewUser: ImageView = view.findViewById(R.id.imageViewUser)

    fun bind(itemJava: JavaRepo?) {
        showItems(itemJava)

        view.setOnClickListener {
            val context = view.context
            context.startActivity(
                PullRequestActivity.startActivity(
                    context,
                    itemJava?.login,
                    itemJava?.name
                )
            )
        }
    }

    private fun showItems(javaRepo: JavaRepo?) {
        repoName.text = javaRepo?.fullName
        repoDescription.apply {
            isVisible = javaRepo?.description != null
            text = javaRepo?.description
        }
        countStar.text = javaRepo?.startCount
        countBranch.text = javaRepo?.forksCount
        userName.text = javaRepo?.name

        Picasso.get()
            .load(javaRepo?.avatar)
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