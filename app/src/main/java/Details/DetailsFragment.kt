package Details

import Favourite.SharedFavouriteVM
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastyfinalproject.R
import com.example.tastyfinalproject.databinding.FragmentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealViewModel by viewModels()
    private lateinit var viewModelfv: SharedFavouriteVM

    private val sharedViewModel: SharedFavouriteVM by activityViewModels()

    private var isVideoPlaying = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedViewModel.selectedMealId.observe(viewLifecycleOwner, Observer { mealId ->
            mealId?.let {
                // Fetch the meal details using mealId and update the UI
                viewModel.fetchMeal(mealId)
            }
        })


        val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet)
        bottomSheetBehavior.peekHeight = 300
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED


//
//       viewModel.fetchMeal("52772")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)



        val floatingButton = view.findViewById<FloatingActionButton>(R.id.video)
        binding.videoOverlay.setOnClickListener {
            binding.videoOverlay.visibility = View.GONE
            floatingButton.visibility = View.VISIBLE
            var isVideoPlaying = false
        }
        viewModelfv= ViewModelProvider(requireActivity()).get(SharedFavouriteVM::class.java)


        binding.favourite.setOnClickListener {
            val mealId = "52977"
            viewModelfv.setSelectedMealId(mealId)        }

        viewModel.meal.observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                binding.mealtitle.text = it.strMeal
                binding.recipedetails.text = it.strInstructions
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.image)
            }
        })
        // Observe ingredients data
        viewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            val adapter = IngredientAdapter(ingredients)
            recyclerView.adapter = adapter
        })

        viewModel.youtubeUrl.observe(viewLifecycleOwner, Observer { youtubeUrl ->
            binding.video.setOnClickListener {
                if (youtubeUrl != null) {
                    if (!isVideoPlaying) {
                        // Hide the button and show the WebView overlay
                        floatingButton.visibility = View.GONE
                        binding.videoOverlay.visibility = View.VISIBLE
                        binding.videoWebView.settings.javaScriptEnabled = true
                        binding.videoWebView.webViewClient = WebViewClient()

                        // Load the video
                        val embedUrl =
                            "https://www.youtube.com/embed/${youtubeUrl.substringAfter("v=")}"
                        binding.videoWebView.loadUrl(embedUrl)
                        var isVideoPlaying = true
                    }
                }
            }
        })
        binding.videoOverlay.setOnClickListener {
            binding.videoOverlay.visibility = View.GONE
        }


    }

    }


