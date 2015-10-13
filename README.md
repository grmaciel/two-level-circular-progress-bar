# Android Two Level Circular Progress Bar
A simple widget extending android.view.View that allows a circular progress bar, with multiple customizations and also a possibility to add a image to 
its center.

![Application screenshot]()

## License
Use it the way you want it :)

## Layout
Make sure you have attrs.xml

Xml declaration

	<br.com.gilson.tlcpb.widget.TwoLevelCircularProgressBar
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:tlcp_drawable="@drawable/ic_bike"
        app:tlcp_progress_color="@color/blue"
        app:tlcp_strokeWidth="15dp"/>
            
## Attributes
There are some attributes defined in the attrs.xml. <br>
```tlcp_bg_color```  - defines the circular progress background<br>
```tlcp_drawable``` - defines the centralized image<br>
```tlcp_progress_color``` - defines the first progress bar color<br>
```tlcp_progress2_color``` - - defines the second progress bar color<br>
```tlcp_strokeWidth``` - defines the circle stroke width<br>
```tlcp_progress``` - defines the first progress value (0 / 100)<br>
```tlcp_progress2``` - defines the second progress value (0 / 100)<br>

## Usage
Inorder to set the circularProgressBar attribute from the xml, make sure your layout xml contains an additional namespace.

	xmlns:app="http://schemas.android.com/apk/res/your_package_name"
	
Progression level:
```
myCircularProgress.setProgressValue(value)
myCircularProgress.setProgress2Value(value)
```

## What's next
* Centralized text <bn>
* Text bellow image
