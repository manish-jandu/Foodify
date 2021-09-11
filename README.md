# Foodify
An app to show different food recipes, can look recipe overview, ingredients and the method of how the recipe is made.It has feature to Favourite and Unfavourite any particular recipes.

It is  offline first app which fetche recipes from Spoonacular api and store it in room database. So after first launch it always launch data from room and fetches only if needed.It has feature to Favourite and Unfavourite any particular recipes.
It is based on MVVM and repository Pattern with the use all modern libraies.

## Built With
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [DataBinding and ViewBinding](https://developer.android.com/topic/libraries/data-binding) -  Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
 - [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. 
- [ViewPager2](https://developer.android.com/guide/navigation/navigation-swipe-view-2) - Create swipe views with tabs using ViewPager2
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines
- [Shimmer](https://facebook.github.io/shimmer-android/) - Shimmer is an Android library that provides an easy way to add a shimmer effect to any view in your Android app
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
  
## Api
* [Spoonacular Api](spoonacular.com/)

## Package Structure

    App                                 # Root UI
    ├── adapters                        # Different Adapters
    ├── bindingAdapters                 # Different Binding Adapters
    |
    ├── data                            # For data handling
    |   ├── database                    # Local Persistence Database[i.e. Room].
    |   ├── network                     # Network Api
    |   |-- repo                        # Repository with different sources
    |
    ├── di                              # Hilt di modules
    |
    ├── Models                          # Model classes
    |
    ├── ui                              # Fragments
    |   ├──  fragments
    |   |     ├── favourite
    |   |     ├── foodjoke
    |   |     ├── instructions
    |   |     ├── overview
    |   |     ├── recipes
    |   |     |-- MainActitvity
    
    ├── uitls                           # Util file like constants, extension func, NetworkListeners
    |
    ├── view models                     # viewmodels
    
 ## Installation

* To run this code, clone this repository using this command `git clone https://github.com/manish-jandu/Foodify.git`
* Import into android studio
* Setup Api key in local.properties
  - API_KEY="**********"
  or set it up in utils/Constants as API_KEY = "****************"
* Build the project and run on an android device or emulator

