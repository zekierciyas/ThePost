package com.zekierciyas.thepost.features.posts_list


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zekierciyas.thepost.R
import com.zekierciyas.thepost.common.connectivity.ConnectivityDetectorImpl
import com.zekierciyas.thepost.domain.Photo
import com.zekierciyas.thepost.domain.Post
import com.zekierciyas.thepost.common.picasso.PicassoBuilder
import com.zekierciyas.thepost.databinding.FragmentPostsListBinding
import com.zekierciyas.thepost.databinding.ListItemPostBinding
import com.zekierciyas.thepost.viewmodel.PostsViewModel


class PostsListFragment : Fragment() {

    lateinit var mContext: Context

    private val viewModel: PostsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProviders.of(this, PostsViewModel.Factory(activity.application))
            .get(PostsViewModel::class.java)
    }

    private var viewModelAdapter: PostAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.allPost.observe(viewLifecycleOwner, Observer<List<Post>> {
            it?.apply {
                viewModelAdapter?.posts = it
            }
        })

        viewModel.allPhotos.observe(viewLifecycleOwner, Observer<List<Photo>> {
            it?.apply {
                viewModelAdapter?.photos = it
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPostsListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_posts_list, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        mContext = context!!

        viewModelAdapter =
            PostAdapter(PostClick {
                findNavController().navigate(
                    PostsListFragmentDirections.actionPostsListFragmentToPostDetailFragment(
                        it
                    )
                )
            })

        binding.root.findViewById<RecyclerView>(R.id.rcvPosts).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        return binding.root
    }
}

class PostViewHolder(val viewDataBinding: ListItemPostBinding) : RecyclerView.ViewHolder(
    viewDataBinding.root
) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_post
    }
}

class PostAdapter(private val callback: PostClick) : RecyclerView.Adapter<PostViewHolder>() {

    var posts: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var photos: List<Photo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val withDataBinding: ListItemPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), PostViewHolder.LAYOUT,
            parent, false)
        return PostViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.post = posts[position]
            it.postCallback = callback
            it.loaderImagePost.visibility = View.VISIBLE
            if (photos.isNotEmpty()) {
                PicassoBuilder
                    .loadImage(photos[position].thumbnailUrl,
                        holder.itemView.context,
                        it.imagePost,
                        R.drawable.ic_error,
                        10,0,
                        it.loaderImagePost,
                        ConnectivityDetectorImpl(holder.itemView.context)
                    )
            }
        }
    }
}

class PostClick(val block: (Post) -> Unit) {
    fun onClick(post: Post) = block(post)
}