package com.example.zadanie_frigma.ui.home.RecyclerViews

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie_frigma.R
import com.squareup.picasso.Picasso


class RvAdapter(private var languageList: List<Language>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {


    /*ВНИМАНИЕ
    SingleItem1Binding - это SingleItem1.xml
     */




    // создайте внутренний класс с именем ViewHolder
    // он принимает аргумент view, в котором передается сгенерированный класс single_item.xml
    // т.е. Привязка к одному элементу и в RecyclerView.ViewHolder(binding.root) передаем его следующим образом


    // внутри onCreateViewHolder раздувать представление привязки к одному элементу
    // и возвращает новый объект ViewHolder, содержащий этот макет
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item1, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = languageList[position]

        // sets the image to the imageview from our itemHolder class


        holder.name.text = ItemsViewModel.name
        holder.text.text = ItemsViewModel.text
        holder.prace.text = ItemsViewModel.prace
        //holder.img.setImageResource()

        val photoView = ItemsViewModel.img
        Picasso.get().load(Uri.parse(ItemsViewModel.img)).placeholder(R.drawable.ic_dashboard_black_24dp).error(R.drawable.ic_dashboard_black_24dp).into(holder.img)
        //holder.body.setBackgroundColor(Color.parseColor("#444444"));
        //holder.expanded_view.visibility = if (ItemsViewModel.expand) View.VISIBLE else View.GONE
        // on Click of the item take parent card view in our case
        // revert the boolean "expand"
        //.setBackgroundColor(R.color.green)

        //holder.card_layout.setOnClickListener {

        //}

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return languageList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val text: TextView = itemView.findViewById(R.id.text)
        val img: ImageView = itemView.findViewById(R.id.img)
        val prace: TextView = itemView.findViewById(R.id.price)


        //val expanded_view: RelativeLayout = itemView.findViewById(R.id.expanded_view)
        //var card_layout: MaterialCardView = itemView.findViewById(R.id.cardView3)
        //var body: ConstraintLayout = itemView.findViewById(R.id.body)
    }


}
