package com.pmirkelam.cointracker.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.RefreshService
import com.pmirkelam.cointracker.coins.ui.CoinsFragmentDirections
import com.pmirkelam.cointracker.databinding.ActivityMainBinding
import com.pmirkelam.cointracker.databinding.NavHeaderMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHeaderBinding: NavHeaderMainBinding =
            NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        navHeaderBinding.viewModel = viewModel

        val toolbar: Toolbar = binding.includeAppBar.toolbar
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.coinListFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = binding.navView
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_favorites ->
                navController.navigate(CoinsFragmentDirections.actionCoinListFragmentToNavFavorites())
            R.id.nav_log_out -> {
                navController.navigate(CoinsFragmentDirections.actionCoinListFragmentToLoginFragment())
                viewModel.clearUser()
            }
        }
        drawerLayout.close()
        return true
    }

    override fun onPause() {
        startService(Intent(this, RefreshService::class.java))
        super.onPause()
    }
}
