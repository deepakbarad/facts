package adapters

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.poc.facts.R
import global.App
import kotlinx.android.synthetic.main.row_fact.view.*
import models.Fact
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import downloader.FactsImageDownloader


class FactsRecyclerViewAdapter(val context:Context) : RecyclerView.Adapter<FactsRecyclerViewAdapter.ViewHolder>()
{
    lateinit var facts:MutableList<Fact>;

    init {
        this.facts = mutableListOf();
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val view:View = LayoutInflater.from(context).inflate(R.layout.row_fact,parent,false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return this.facts.size;
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val fact:Fact = this.facts.get(position);
        viewHolder.tvFactTitle.text = fact.title;
        viewHolder.tvDescription.text = fact.description;
        viewHolder.ivFactPhoto.setImageDrawable(ContextCompat.getDrawable(viewHolder.ivFactPhoto.context,R.drawable.ic_image_blank));
        FactsImageDownloader.loadImage(fact.imageHref,viewHolder.ivFactPhoto)
    }

    fun clear()
    {
        val currentSize:Int = this.facts!!.size;
        this.facts!!.clear()
        notifyItemRangeRemoved(0,currentSize-1);
    }

    fun addFact(fact:Fact)
    {
        this.facts!!.add(fact);
        notifyItemInserted(this.facts!!.size-1);
    }

    fun addFacts(facts:List<Fact>)
    {
        this.facts = facts.toMutableList();
        notifyDataSetChanged();
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val tvFactTitle = view.tvFactTitle;
        val tvDescription = view.tvDescription;
        val ivFactPhoto = view.ivFactPhoto;
    }
}