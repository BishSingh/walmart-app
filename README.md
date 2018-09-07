# walmart-app

### Description 
Native Android App that implements Walmart's product search and Product Recommendations using **Walmart Labs Open Apis**. Built with latest Adroid Architecture Components of Android JetPack. 

UX of the app was designed by me keeping **Walmart Branding Guide** in mind.

### Apk Direct Download Link

https://github.com/BishSingh/walmart-app/blob/master/walmart-app-debug.apk

### Testing the app
- Download the apk from link above. 
- Look for **Walmart** app after the install.
- Search any product you would like to buy at Walmart.
- Clicking on search item will take to product recommendatios page.

### App Video Recording:

https://github.com/BishSingh/walmart-app/blob/master/2018_09_07_00_11_58.mp4

### Some minor things to observe

- Uses Walmart Bagel Font.Colors and assets are all taken from Branding guide.
- Supports Landscape & Portrait Rotation. Performance got optimzed due to use of Android ViewModels.
- Error state handling when a product is not found
- Each item in the product recommendation is clickable
- Maintains only one instance of product reco activity
- Showing only 10 items as it was a requirement for now

App has used latest Android technologies. Below are a list of components in use:
- Archtecture Components : Android Jetpack https://developer.android.com/jetpack/
- Android ViewModels: https://developer.android.com/topic/libraries/architecture/viewmodel
- LiveData: https://developer.android.com/topic/libraries/architecture/livedata
- Dagger2 for dependency injection: https://github.com/google/dagger
- Picasso for Image Decoding: http://square.github.io/picasso/
- Retrofit2 : https://square.github.io/retrofit/
- okHttp3 : http://square.github.io/okhttp/
- ButterKnife : http://jakewharton.github.io/butterknife/
- gson : https://github.com/google/gson
- Timber : https://github.com/JakeWharton/timber

### App screenshots

![alt text](https://github.com/BishSingh/walmart-app/blob/master/Screenshot_20180906-235826.png)

![alt text](https://github.com/BishSingh/walmart-app/blob/master/Screenshot_20180906-235907.png)

![alt text](https://github.com/BishSingh/walmart-app/blob/master/Screenshot_20180907-000053.png)

![alt text](https://github.com/BishSingh/walmart-app/blob/master/Screenshot_20180907-004309.png)





  
  
