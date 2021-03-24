package com.pmirkelam.cointracker.utils

//    @BindingAdapter("onTextChanged")
//    fun onTextChanged(sw: SearchView, i: Int) {
//        sw.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                if (query.trim().isEmpty() && query.length > 2) {
//                    _filtered.value = true
//                    getFilteredCoins(query)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                if (newText.trim().isEmpty() && newText.length > 2) {
//                    _filtered.value = true
////                    repository.searchCoin(
////                        newText
////                    )
//                }
//                return false
//            }
//        })
//    }