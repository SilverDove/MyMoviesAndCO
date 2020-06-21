# MyMoviesAndCO
Clara Tricot - 3rd Year at ESIEA

## Introduction
This project is an Android application helping the users to track the movies they are watching or they want to watch. 
It uses the online database TMDB as a API to access a list of movies.
It is intuitive and optimized for the ease of everyone. Enjoy it!


## Getting Started
1. Download and install [Android Studio](https://developer.android.com/studio/)
2. Clone or download the project via the [master branch](https://github.com/SilverDove/MyMoviesAndCO.git)

## Features

### Must Have
* One screen displaying a list of elements
* One screen displaying the details of one element
* API Rest Call
* Stockage of data in the cache

### What I implemented
* Four screeens: 3 fragments and 2 activities
    * Home page
    * Search Page
    * Watchlist
    * DetailsMovie
    * Splash Screen
* API Rest call from TMDB API
* Room Database
* Bottom Navigation using fragments
* SearchBar with a filter
* Icon app
* MVC architecture
* Singleton
* Principles (KISS, SOLID)
* GitFlow up-to-date
* Copyright

 ## Overview
 ### Spash Screen
  It appears each time the user launch the application. It shows the application logo and other information during 5 seconds
 ![alt text](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Splash_Screen.png)
 
 ### Home Page
 It displays the current popular movies from TMDb. This list updates daily.

 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Home_Page.png)
 
 * **No Internet connection:** When there is no internet connection, the list of the current popular movies can't be displayed
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/HomePage_NoInternet.png)
 
  
 ### Details Movie
 After clicking on a specific movie from the list, it displays extra information about this movie. It's from this page that the user can add or remove a movie from the watchlist. When the movie is added in the watchlist, the user can indicate if the movie was watched or not.
     
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/DetailsMovie.png)
 
 * **Add into the watchlist:** To add a movie in the watchlist, the user need to click on the icon at the top right of the screen. Then, the icon changes to indicate that the movie is already in the watchlist. Moreover, a toast appear during few seconds and the watched/unwatched option appears.
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/DetailsMovie_Add.png)
 
 * **Watched option activated:** The user can indicate if they've already watched or not a movie. This option is available only when the movie is in the watchlist
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/DetailsMovie_Watched.png)
 
 * **Remove from the watchlist:** To remove a movie from the watchlist, the user need to click on the icon at the top right of the screen. Then, the icon changes to indicate that the movie is not in the watchlist. Moreover, a toast appear during few seconds and the watched/unwatched option disapears.
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/DetailsMovie_Remove.png)
         
 ### Watchlist
 This page contains the movies the user wants to watched or already watched. The user can filter the list by using the searchBar or using the navigation at the top right corner (all, watched, unwatched).Of course, the user can access to the details of each movie. This page is the only one that can be accessible offline. 
 
 * **Navigation:** The user can filter the list by displaying all the movies , watched movies or unwatched movies.
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Watchlist_Navigation.png)
 
 * **All movies:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/WatchList_All.png)
 
 * **Movies watched:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Watchlist_Watched.png)
 
 * **Movies Unwatched:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Watchlist_Unwatched.png)
 
 * **Search bar navigation:** The user can use the search bar to find quickly a movie in the watchlist by typing the movie name
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Watchlist_Search1.png)
 
 * **Empty watchlist:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/Watchlist_Empty.png)
 
 ### Search Page
 The user can also search movies from the TMDB to add them into the watchlist or display their information.
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/SearchPage.png)
 
 * **Search:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/SearchPage_search.png)
 
 * **No Internet connection:** When there is no internet connection, the user can't search movies
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/SeachPage_NoInternet.png)
 
 * **Incorrect movies:**
 
 ![](https://github.com/SilverDove/MyMoviesAndCO/blob/master/ScreenShot_MyMovies%26Co/SearchPage_Incorrect.png)
 
  ## Authors
  * **Clara Tricot** - *MyMovie&Co* - [SilverDove](https://github.com/SilverDove)
 
 ## License
 This project is licensed under the MIT license.

 Copyright (c) 2020 Clara Tricot
 
 ## Acknowledgments
 Inspired by the Youtuber [Coding in Flow](https://codinginflow.com/) and my previous project [Price Tracker](https://github.com/SilverDove/Price_Tracker)
  
  
