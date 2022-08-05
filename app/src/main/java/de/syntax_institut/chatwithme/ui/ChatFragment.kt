package de.syntax_institut.chatwithme.ui // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax_institut.chatwithme.adapter.MessageAdapter
import de.syntax_institut.chatwithme.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    // Hier wird das ViewModel, in dem die Logik stattfindet, geholt
    private val viewModel: SharedViewModel by activityViewModels()

    // Das binding für das QuizFragment wird deklariert
    private lateinit var binding: FragmentChatBinding

    /**
     * Lifecycle Funktion onCreateView
     * Hier wird das binding initialisiert und das Layout gebaut
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Lifecycle Funktion onViewCreated
     * Hier werden die Elemente eingerichtet und z.B. onClickListener gesetzt
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mit binding wird das ViewModel und der viewLifecycleOwner dem Layout zugewiesen
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Das übergebene Argument ("contact Index") wird in eine Variable gespeichert
        val contactIndex = requireArguments().getInt("contactIndex")

        // Über die Funktion aus dem ViewModel wird der Chat initialisiert
        viewModel.initializeChat(contactIndex)

        // Anhand der Informationen, die im currentContact im ViewModel gespeichert sind, wird das Profilbild und der Profilname gesetzt

        binding.ivContactPicture.setImageResource(viewModel.currentContact.imageResId)

        binding.tvContactName.text = viewModel.currentContact.name

        // Die Variable aus dem ViewModel, in der der TextInput gespeichert ist wird beobachtet
        viewModel.inputText.observe(viewLifecycleOwner) {
            viewModel.inputTextChanged(it)
        }

        // Der MessageAdapter für die RecyclerView wird erstellt und in einer Variablen gespeichert
        val msgAdapter = MessageAdapter(viewModel.currentContact.chatHistory, requireContext())

        // Der Adapter wird der RecyclerView zugewiesen
        binding.rvMessages.adapter = msgAdapter

        // Der draftMessageState aus dem ViewModel wird beobachtet um je nach Zustand den Adapter zu benachrichtigen
        viewModel.draftMessageState.observe(viewLifecycleOwner) {
            when (it) {
                DraftState.CREATED -> {
                    msgAdapter.notifyItemInserted(0); binding.rvMessages.scrollToPosition(0)
                }
                DraftState.CHANGED -> msgAdapter.notifyItemChanged(0, Unit)
                DraftState.SENT -> msgAdapter.notifyItemChanged(0, Unit)
                DraftState.DELETED -> msgAdapter.notifyItemRemoved(0)
            }
        }

        // Der btnSend bekommt einen Click Listener
        binding.btnSend.setOnClickListener {
            if (viewModel.inputText.value == "") {
                Toast.makeText(context, "NO TEXT INPUT", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendDraftMessage()
            }
        }

        // Der BtnBack bekommt einen Click Listener
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /**
     * Lifecycle Funktion onDestroy
     * Diese Funktion wird aufgerufen wenn das Fragment beendet wird
     */
    override fun onDestroy() {
        super.onDestroy()

        // Über die ViewModel Funktion wird der Chat geschlossen
        viewModel.closeChat()
    }
}
