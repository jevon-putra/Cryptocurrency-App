package com.jop.cointracker.ui.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jop.cointracker.view.home.screen.HomeScreen

@Composable
fun NavigationRoute(modifier: Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MAIN,
        enterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        exitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) },
        popEnterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        popExitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) }
    ){
        composable(Route.MAIN){
            HomeScreen(navController)
        }
        composable(Route.DETAIL_COIN){

        }
    }
}