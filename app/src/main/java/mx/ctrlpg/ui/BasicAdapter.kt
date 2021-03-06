package mx.ctrlpg.ui

import mx.ctrlpg.data.model.SucursalCordon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import mx.ctrlpg.R

class BasicAdapter(private val myDataset: List<SucursalCordon>) :
        RecyclerView.Adapter<BasicAdapter.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val textView: View) : RecyclerView.ViewHolder(textView)

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): BasicAdapter.MyViewHolder {
            // create a new view
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.text_view_item, parent, false)

            // set the view's size, margins, paddings and layout parameters

            return MyViewHolder(textView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            val myText = holder.textView.findViewById<TextView>(R.id.my_text)
            myText.text = myDataset[position].sucDescripcion
            myText.setOnClickListener{
                val bundle = bundleOf("cordon" to myDataset[position])
                it.findNavController().navigate(R.id.action_event_fragment_to_cordon_fragment, bundle)
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }

