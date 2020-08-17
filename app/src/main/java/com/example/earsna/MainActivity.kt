package com.example.earsna

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.example.earsna.util.PreferenceHelper
import com.example.earsna.util.PreferenceHelper.set
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    companion object{
        const val USER_ROLE = 0
        const val PARTENER_ROLE = 1
        const val ADMIN_ROLE = 2
    }

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    var firebaseAuth  = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // user is logged in so prepare the mainactivity    else move to the login or register (onboarding activity)
        if(firebaseAuth.currentUser != null) initMainactivity()
        else {
            var intent = Intent(this , OnboardingActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
            R.id.logout_menu_item ->{
                firebaseAuth.signOut()
                var preference = PreferenceHelper.getInstance(this)
                preference["USER_ROLE"] = -1
                preference["USER_NAME"] = ""
                preference["PHONE_NUMBBER"] = ""
                var intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                true
            }
            else ->{
               super.onOptionsItemSelected(item)
           }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun initMainactivity(){
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // add nececcary menu based on role
        var preference = PreferenceHelper.getInstance(this)
        var role = preference.getInt("USER_ROLE" , 0)
        var userName = preference.getString("USER_NAME" , "")
        var phone_number = preference.getString("PHONE_NUMBBER" , "")
        var nametextview : TextView = navView.getHeaderView(0).findViewById(R.id.name_textview)
        var phone_number_textview : TextView = navView.getHeaderView(0).findViewById(R.id.phone_number_textview)
        nametextview.text = userName
        phone_number_textview.text = phone_number
        phone_number_textview.text = phone_number

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.admin_nav_dashboard ,
            R.id.partener_nav_dashboard, R.id.partener_nav_report, R.id.partener_nav_payment , R.id.nav_booking_list), drawerLayout)
         navController = findNavController(R.id.main_nav_host_fragment)

        when(role){
            PARTENER_ROLE -> {
                navView.menu.removeItem(R.id.user_nav_home)
                navView.menu.removeItem(R.id.admin_nav_dashboard)
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }
            ADMIN_ROLE -> {
                navView.menu.removeItem(R.id.user_nav_home)
                navView.menu.removeItem(R.id.partener_nav_dashboard)
                navView.menu.removeItem(R.id.partener_nav_payment)
                navView.menu.removeItem(R.id.partener_nav_report)
                navView.menu.removeItem(R.id.nav_booking_list)


                var adminGraph =  navController.navInflater.inflate(R.navigation.main_navigation)
                adminGraph.startDestination = R.id.admin_nav_dashboard
                navController.graph = adminGraph
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }
            else -> {
                navView.menu.removeItem(R.id.partener_nav_dashboard)
                navView.menu.removeItem(R.id.partener_nav_payment)
                navView.menu.removeItem(R.id.partener_nav_report)
                navView.menu.removeItem(R.id.admin_nav_dashboard)

                var customerGraph = navController.navInflater.inflate(R.navigation.main_navigation)
                customerGraph.startDestination = R.id.user_nav_home
                navController.graph = customerGraph
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }
        }

    }
}