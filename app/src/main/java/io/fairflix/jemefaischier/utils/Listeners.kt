package io.fairflix.jemefaischier.utils

interface OnItemClickListener<T> {
    fun onItemClick(item: T)
}

interface OnItemDeleteListener<T> {
    fun onItemDelete(item: T)
}
