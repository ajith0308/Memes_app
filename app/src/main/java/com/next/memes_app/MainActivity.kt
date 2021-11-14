package com.next.memes_app

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.util.*


class MainActivity:AppCompatActivity()
{
    var currenturl : String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()


    }
    fun load()
    {
        val ProgressBar =findViewById<ProgressBar>(R.id.progress)
        val ImageView=findViewById<ImageView>(R.id.imageView)
        ProgressBar.visibility=View.VISIBLE


        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                  currenturl=response.getString("url")
                Glide.with(this).load(currenturl).listener(object :RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility=View.GONE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility=View.GONE
                        return false
                        //
                    }

                }).into(ImageView)
            },
            { _ ->
                Toast.makeText(this,"connet to network",Toast.LENGTH_LONG).show()
                // TODO: Handle error
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,currenturl)
        val chooser=Intent.createChooser(intent,"share meam")
        startActivity(chooser)



    }
    fun next(view: View) {
    load()
    }
}


