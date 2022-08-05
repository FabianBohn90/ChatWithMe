package de.syntax_institut.chatwithme.adapter // ktlint-disable package-name

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import de.syntax_institut.chatwithme.R
import de.syntax_institut.chatwithme.data.model.Message

class MessageAdapter(
    private val dataset: List<Message>,
    private val context: Context
) : RecyclerView.Adapter<MessageAdapter.ItemViewHolder>() {

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvText: TextView = itemView.findViewById(R.id.tvMessageText)
        val cvMessage: CardView = itemView.findViewById(R.id.cvMessage)
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_message, parent, false)

        return ItemViewHolder(itemLayout)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Die aktuelle Message wird aus dem Dataset geholt
        val message = dataset[position]

        // Die Nachricht und die Transparenz (abhängig davon ob es sich um einen Draft handelt) werden gesetzt
        holder.tvText.text = message.messageText
        if (message.isDraft) {
            holder.cvMessage.alpha = 0.3f
        } else {
            holder.cvMessage.alpha = 1f
        }

        // Die CardView bekommt einen onLong Click Listener, in dem ein Share Intent erstellt wird
        // BONUS
        holder.cvMessage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, holder.tvText.text)
            intent.type = "text/plain"
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(context, shareIntent, null)
            true
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}
