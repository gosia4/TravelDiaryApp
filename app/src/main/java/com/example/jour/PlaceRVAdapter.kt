package com.example.jour

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import androidx.recyclerview.widget.RecyclerView
import com.example.jour.MVVM.Place

class PlaceRVAdapter(
    val context: Context,
    val noteClickEditInterface: NoteClickEditInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
): RecyclerView.Adapter<PlaceRVAdapter.ViewHolder>( ){

    private val allEntries = ArrayList<Place>()
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTV =itemView.findViewById<TextView>(R.id.TitleTextView)
        val descTV= itemView.findViewById<TextView>(R.id.descTextView)
        val dateTV =itemView.findViewById<TextView>(R.id.dateTextView)
        val ratingTV =itemView.findViewById<TextView>(R.id.rating)
        val deleteTV=itemView.findViewById<ImageView>(R.id.deleteImage)
        val noteImageView = itemView.findViewById<ImageView>(R.id.noteImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTV.setText(allEntries.get(position).jourTitle)
        holder.descTV.setText(allEntries.get(position).jourDescription)
        holder.dateTV.setText(allEntries.get(position).jourDate)
        val ratingText = "Rating: ${allEntries[position].rating}"
        holder.ratingTV.text = ratingText
        holder.deleteTV.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allEntries.get(position))
        }
        holder.itemView.setOnClickListener{
            noteClickEditInterface.onNoteClick((allEntries.get(position)))
        }

        val bmp = allEntries[position].jourImage
        if (bmp != null) {
            Glide.with(context)
                .load(bmp)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image while loading
                .into(holder.noteImageView)
            holder.noteImageView.visibility = View.VISIBLE
        } else {
//            holder.noteImageView.visibility = View.GONE

            // Show a placeholder or set a background color
//            holder.noteImageView.setBackgroundResource(androidx.appcompat.R.color.material_blue_grey_800)
//             or
             holder.noteImageView.setImageResource(R.drawable.blue_image)
            holder.noteImageView.visibility = View.VISIBLE

        }
    }

    override fun getItemCount(): Int {
        return allEntries.size
    }

    fun updateList(newList: List<Place>){
        allEntries.clear()
        allEntries.addAll(newList)
        notifyDataSetChanged()
    }
}

interface  NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Place)
}
interface  NoteClickEditInterface{
    fun onNoteClick(note: Place)
}