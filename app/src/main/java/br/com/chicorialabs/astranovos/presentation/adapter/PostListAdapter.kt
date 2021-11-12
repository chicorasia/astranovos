package br.com.chicorialabs.astranovos.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.databinding.ItemPostBinding

/**
 * Essa classe define um adaptaer para a RecyclerView de Posts. Optei por
 * usar um ListAdapter para melhorar o desempenho visual da lista.
 * Como é um ListAdapter eu também não preciso manter um campo list
 * na classe Adapter.
 */
class PostListAdapter : ListAdapter<Post, PostListAdapter.PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Optei por usar uma classe aninhada ao invés de classe interna para poder
     * manter duas boas práticas: (1) inflar o layout a partir da própria classe
     * ViewHolder e (2) delegar o binding dos dados para a própria classe ViewHolder.
     */
    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            /**
             * Esse método infla o layout e retorna o ViewHolder. É uma boa prática
             * que reduz o acoplamento do código do adapter.
             */
            fun from(parent: ViewGroup) : PostViewHolder {
                val binding: ItemPostBinding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                false
                )
                return PostViewHolder(binding)
            }
        }

        /**
         * Como estou usando um databainding layout, nesse método eu atribuo
         * o objeto Post ao à varáivel "post" definida no XML. Eventualmente
         * vou precisar adicionar um campo clickListener para tratar os cliques
         * no item.
         */
        fun bind(item: Post) {
            binding.post = item
        }

    }

    /**
     * Essa classe mantém os métodos obrigatórios para o DiffUtil
     * e é praticamente boilerplate.
     */
    private class PostDiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

}