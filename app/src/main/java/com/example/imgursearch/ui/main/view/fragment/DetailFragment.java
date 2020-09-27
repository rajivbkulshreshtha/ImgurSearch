package com.example.imgursearch.ui.main.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.RequestManager;
import com.example.imgursearch.R;
import com.example.imgursearch.data.model.Image;
import com.example.imgursearch.other.Resource;
import com.example.imgursearch.ui.main.viewmodel.ImgurViewModel;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DetailFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String TAG = "DetailFragment";

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private Toolbar toolbar;
	private ImageView imageView;
	private EditText etComment;
	private Button btSubmit;

	private Image image;
	private RequestManager glide;
	private ImgurViewModel viewModel;


	public DetailFragment() {
		// Required empty public constructor
	}


	public DetailFragment(RequestManager glide) {
		this.glide = glide;
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment DetailFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static DetailFragment newInstance(String param1, String param2) {
		DetailFragment fragment = new DetailFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);

			image = DetailFragmentArgs.fromBundle(getArguments()).getImage();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_detail, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		NavBackStackEntry backStackEntry = Navigation.findNavController(requireView()).getBackStackEntry(R.id.nav_graph);
		ViewModelProvider viewModelProvider = new ViewModelProvider(
				backStackEntry,
				getDefaultViewModelProviderFactory()
		);

		viewModel = viewModelProvider.get(ImgurViewModel.class);

		setupView();
		viewModel.localImage(image);
		subscribeToObservers();
		clickHandler();
	}

	private void setupView() {
		toolbar = requireView().findViewById(R.id.toolbar);
		imageView = requireView().findViewById(R.id.imageView);
		etComment = requireView().findViewById(R.id.etComment);
		btSubmit = requireView().findViewById(R.id.btSubmit);


		try {
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		} catch (Exception e) {
			Log.d(TAG, "setupView: error => " + e.getLocalizedMessage());
		}

	}

	private void subscribeToObservers() {
		viewModel.getImage().observe(getViewLifecycleOwner(), resource -> {
			Resource<Image> imageResource = resource.getContentIfNotHandled();
			if (imageResource != null) {

				switch (imageResource.getStatus()) {
					case SUCCESS: {
						try {
							((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(
									imageResource.getData().getTitle() != null
											? imageResource.getData().getTitle() : getString(R.string.label_toolbar_detail_placeholder_title));
						} catch (Exception e) {
							Log.d(TAG, "subscribeToObservers: error => " + e.getLocalizedMessage());
						}
						etComment.setText(imageResource.getData().getComment() != null
								? imageResource.getData().getComment() : "");

						glide.load(imageResource.getData().getImageUrl()).into(imageView);
						break;
					}
					case ERROR:
						glide.load(R.drawable.ic_warning).into(imageView);

						Snackbar snackbar = Snackbar.make(
								requireActivity().findViewById(R.id.rootLayout),
								R.string.failed_unknown_error,
								Snackbar.LENGTH_SHORT
						);
						snackbar.getView().setBackgroundColor(Color.RED);
						snackbar.show();
						break;
					case LOADING: {
						CircularProgressDrawable cp = new CircularProgressDrawable(requireContext());
						cp.setStrokeWidth(5f);
						cp.setCenterRadius(30f);
						cp.start();
						glide.load(cp).into(imageView);
						break;
					}
				}
			}
		});

		viewModel.getCommentSaved().observe(getViewLifecycleOwner(), resource -> {
					Resource<Boolean> booleanResource = resource.getContentIfNotHandled();
					if (booleanResource != null) {
						switch (booleanResource.getStatus()) {
							case SUCCESS: {
								Snackbar snackbar = Snackbar.make(
										requireActivity().findViewById(R.id.rootLayout),
										R.string.success_save_comment,
										Snackbar.LENGTH_SHORT
								);
								snackbar.getView().setBackgroundColor(Color.parseColor("#1BB76E"));
								snackbar.show();
								break;
							}
							case ERROR: {
								Snackbar snackbar = Snackbar.make(
										requireActivity().findViewById(R.id.rootLayout),
										R.string.failed_save_comment,
										Snackbar.LENGTH_SHORT
								);
								snackbar.getView().setBackgroundColor(Color.RED);
								snackbar.show();
								break;
							}
						}
					}
				}
		);
	}


	private void clickHandler() {
		btSubmit.setOnClickListener(v -> {
			if (commentValidator(etComment.getText().toString())) {
				image.setComment(etComment.getText().toString());
				viewModel.saveImage(image);
			} else {
				Snackbar.make(
						requireActivity().findViewById(R.id.rootLayout),
						R.string.failed_valid_comment,
						Snackbar.LENGTH_SHORT
				).show();
			}
		});

		toolbar.setNavigationOnClickListener(v -> {
			requireActivity().onBackPressed();
		});

	}


	protected Boolean commentValidator(String comment) {
		return comment != null && !comment.isEmpty();
	}

}
